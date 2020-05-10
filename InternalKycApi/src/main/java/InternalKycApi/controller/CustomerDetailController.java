package InternalKycApi.controller;

import InternalKycApi.entity.Detail;
import InternalKycApi.repo.DetailRepository;
import InternalKycApi.viewModel.DetailRequestViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/details")
public class CustomerDetailController {


    private final DetailRepository _detailRepository;

    public CustomerDetailController(DetailRepository detailRepository) {

        _detailRepository = detailRepository;

        if (_detailRepository.count() <= 0) {
            Detail detail1 = new Detail();
            detail1.tckn = "10001";
            detail1.email = "customer1@paydaybank.com";
            detail1.address = "Mah Cad Sok Vs";
            detail1.lastEmployer = "Son işveren vs";
            detail1.lastEmployerStartDate = "2018-05-05";
            detail1.previouslyGrantedLoan = 0.0;

            Detail detail2 = new Detail();
            detail2.tckn = "10002";
            detail2.email = "customer2@paydaybank.com";
            detail2.address = "Mah Cad Sok Vs";
            detail2.lastEmployer = "Son işveren vs";
            detail2.lastEmployerStartDate = "2020-05-05";
            detail2.previouslyGrantedLoan = 11000.00;
            _detailRepository.save(detail1);
            _detailRepository.save(detail2);
        }
    }

    @PostMapping
    public ResponseEntity<Detail> post(@Valid @RequestBody DetailRequestViewModel requestViewModel) {
        return ResponseEntity.ok(_detailRepository.findDetailByTckn(requestViewModel.tckn));
    }

    @GetMapping
    public ResponseEntity<Detail> get(@RequestParam String id) {
        return ResponseEntity.ok(_detailRepository.findDetailByTckn(id));
    }
}
