package PayDayBankApi.model.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import javax.validation.constraints.NotNull;

public class DetailServiceRequest extends HttpEntity {
    public DetailServiceRequest(HttpHeaders headers) {
    }
    @NotNull
    public String tckn;

}
