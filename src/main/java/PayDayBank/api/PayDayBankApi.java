package PayDayBank.api;

import PayDayBank.entities.Customer;
import PayDayBank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/PayDayBank")
public class PayDayBankApi {

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    public void init(){
        Customer customer1 = new Customer();
        customer1.setBalance(100.00);
        customer1.setCustomerNo("1001");
        customer1.setCustomerPass("123456");
        customer1.setName("Ahmet");
        customer1.setSurname("Işık");
        customer1.setTckn("100001");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setBalance(200.00);
        customer2.setCustomerNo("1002");
        customer2.setCustomerPass("123456");
        customer2.setName("Mehmet");
        customer2.setSurname("Işık");
        customer2.setTckn("100002");
        customerRepository.save(customer2);
    }
    @PostMapping
    public ResponseEntity<Customer> ekle(@RequestBody Customer customer)
    {
        return  ResponseEntity.ok(customerRepository.save(customer));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listele()
    {
        return  ResponseEntity.ok(customerRepository.findAll());
    }

}
