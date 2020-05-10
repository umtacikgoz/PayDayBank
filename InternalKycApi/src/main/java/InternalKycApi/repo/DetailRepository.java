package InternalKycApi.repo;

import InternalKycApi.entity.Detail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetailRepository extends MongoRepository<Detail,String> {
    Detail findDetailByTckn (String tckn);
}