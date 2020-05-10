package ExternalIncomeApi.controller;

import ExternalIncomeApi.entity.IncomeInfo;
import ExternalIncomeApi.repo.IncomeInfoRepository;
import ExternalIncomeApi.viewModel.IncomeInfoRequestViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeInfoRepository incomeInfoRepository;

    public IncomeController(IncomeInfoRepository incomeInfoRepository) {
        this.incomeInfoRepository = incomeInfoRepository;

        if (incomeInfoRepository.count() <= 0)
        {
            IncomeInfo income = new IncomeInfo();
            income.setTckn("10001");
            income.setMonthlyIncome(1000.00);
            incomeInfoRepository.save(income);

            IncomeInfo income2 = new IncomeInfo();
            income2.setTckn("10002");
            income2.setMonthlyIncome(2000.00);
            incomeInfoRepository.save(income2);
        }
    }

    @PostMapping("")
    public ResponseEntity<String> setIncome(@Valid @RequestBody IncomeInfoRequestViewModel requestViewModel) {

        double monthlyIncome = 0.0;
        List<IncomeInfo> incomes = incomeInfoRepository.findAll();

        for (IncomeInfo income : incomes) {
            //Burası çalışmadı ??
            if (String.valueOf(income.getTckn())  == String.valueOf(requestViewModel.tckn))
            {
                monthlyIncome = income.getMonthlyIncome();
            }
        }
        IncomeInfo info = incomeInfoRepository.findByTckn(requestViewModel.tckn);
        if(info != null)
            monthlyIncome = info.getMonthlyIncome();
        if (monthlyIncome > 0)
            return ResponseEntity.ok(String.valueOf(monthlyIncome));
        else
            return ResponseEntity.ok("0");
            //return ResponseEntity.notFound(requestViewModel.tckn + " no'lu kayıt bulunamadı !");
    }
    @GetMapping
    public ResponseEntity<List<IncomeInfo>> getIncomes() {
            return ResponseEntity.ok(incomeInfoRepository.findAll());
    }
}