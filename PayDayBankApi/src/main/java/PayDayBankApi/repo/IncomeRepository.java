package PayDayBankApi.repo;


import PayDayBankApi.entity.Income;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IncomeRepository extends MongoRepository<Income,String> {
    Income findIncomeByTckn (String tckn);
}