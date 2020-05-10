package PayDayBankApi.repo;

import PayDayBankApi.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer,String> {
    Customer findCustomerByTckn (String tckn);
}