package ExternalIncomeApi.repo;

import ExternalIncomeApi.entity.IncomeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface IncomeInfoRepository extends JpaRepository<IncomeInfo, Long> {
    IncomeInfo findByTckn(String Tckn);
}
