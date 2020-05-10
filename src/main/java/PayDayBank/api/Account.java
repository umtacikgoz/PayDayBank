package PayDayBank.api;

import PayDayBank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Account")
public class Account {

    @Autowired
    private CustomerRepository customerRepository;

}
