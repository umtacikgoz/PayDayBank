package PayDayBankApi.controller;

import PayDayBankApi.entity.Customer;
import PayDayBankApi.entity.Income;
import PayDayBankApi.model.dto.KycDetail;
import PayDayBankApi.model.queue.LoanDemand;
import PayDayBankApi.model.service.IncomeInfoServiceRequest;
import PayDayBankApi.model.view.LoanDemandRequestViewModel;
import PayDayBankApi.repo.CustomerRepository;
import PayDayBankApi.repo.IncomeRepository;
import PayDayBankApi.service.KycDetailService;
import PayDayBankApi.service.LoanDemandProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.sql.Date;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private KycDetailService kycDetailService;
    @Autowired
    private LoanDemandProducer loanDemandProducer;

    private final IncomeRepository _incomeRepository;
    private final CustomerRepository _customerRepository;

    public LoanController(IncomeRepository incomeRepository, CustomerRepository customerRepository) {
        _incomeRepository = incomeRepository;
        _customerRepository = customerRepository;

        if (_customerRepository.count() <= 0) {
            Customer customer1 = new Customer();
            customer1.tckn = "10001";
            customer1.customerId = "0001";
            customer1.customerPass = "123456";
            customer1.name = "customer1 name";
            customer1.surname = "customer1 surname";
            _customerRepository.save(customer1);

            Customer customer2 = new Customer();
            customer2.tckn = "10002";
            customer2.customerId = "0002";
            customer2.customerPass = "123456";
            customer2.name = "customer2 name";
            customer2.surname = "customer2 surname";
            _customerRepository.save(customer2);
        }
    }

    @PostMapping
    public ResponseEntity<String> post(@Valid @RequestBody LoanDemandRequestViewModel requestViewModel) {

        KycDetail detail =     kycDetailService.getDetail(requestViewModel.tckn);

        // Talep eden kişinin tckn'sine ait bir müşteri bankanın kendi postgresql db'sinde var mı ?
        // Default 2 müşteri atadım ilk ilk talepte set edilecek şekilde
        Customer customer = _customerRepository.findCustomerByTckn(requestViewModel.tckn);

        if (customer != null) {

            // Gelir Bilgisi Daha önce external servis'den alınıp kaydedildiyse
            // tekrar servise gidilmiyor internal Mongo Nosql'den alınıyor
            // Ayda bir günceli alsın gibi vs eklemeler yapılabilir
            Income customerIncome = _incomeRepository.findIncomeByTckn(requestViewModel.tckn);

            if (customerIncome == null) {
                IncomeInfoServiceRequest incomeInfoRequest = new IncomeInfoServiceRequest(requestViewModel.tckn);
                ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/incomes", incomeInfoRequest, String.class);

                if (Double.valueOf(response.getBody()) > 0) {
                    Income income = new Income();
                    income.tckn = requestViewModel.tckn;
                    income.income = Double.valueOf(response.getBody());
                    _incomeRepository.save(income);
                    // Gelir Bilgisi external servise tekrar tekrar gidilmesin diye kaydediliyor
                    customerIncome = income;
                } else {

                    return ResponseEntity.ok(requestViewModel.tckn + " national ID'li müşterinin gelir bilgisi bulunmamaktadır !");
                }
                // Kredi Hesaplama

                loanDemandProducer.sendToQueue(getLoanDemand(detail,customerIncome,requestViewModel.requestedAmount));

                return ResponseEntity.ok(customerIncome.toString() +" ile Kredi Hesaplama --");
            } else {

                // Kredi Hesaplama

                loanDemandProducer.sendToQueue(getLoanDemand(detail,customerIncome,requestViewModel.requestedAmount));

                return ResponseEntity.ok(customerIncome.toString() +" ile Kredi Hesaplama ---");
            }

        } else {
            return ResponseEntity.ok(requestViewModel.tckn + " national ID'li bir müşteri bulunmamaktadır !");
        }
    }

    private LoanDemand getLoanDemand(KycDetail detail, Income customerIncome, Double requestedLoan)
    {
        LoanDemand loanDemand = new LoanDemand();

        loanDemand.email = detail.email;
        loanDemand.requestedLoan = requestedLoan;
        loanDemand.income = customerIncome.income;
        loanDemand.interestRate = 10.11; // Banka içi bir servisden vs gelebilir
        loanDemand.previouslyGrantedLoan = detail.previouslyGrantedLoan;
        loanDemand.lastEmployerStartDate = Date.valueOf(detail.lastEmployerStartDate);

        return loanDemand;
    }
}
