package PayDayBankApi.service;

import PayDayBankApi.model.dto.KycDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class KycDetailService implements KycDetailInterface {

    @Autowired
    private RestTemplate restTemplate;

    public KycDetail getDetail(String tckn)
    {
        KycDetail detail = new KycDetail();

        String authStr = "InternalUser:InternalPassword";
        String creds = Base64.getEncoder().encodeToString(authStr.getBytes());

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + creds);

        // create request
        HttpEntity request = new HttpEntity(headers);

        //request.tckn = tckn;

        String url = "http://localhost:8088/details?id="+tckn;
        //ResponseEntity<List> response = restTemplate.exchange ("http://localhost:8088/details", HttpMethod.GET, request, List.class);
        ResponseEntity<KycDetail> response = restTemplate.exchange (url, HttpMethod.GET, request, KycDetail.class);
        // get JSON response
        //String json = response.getBody();
        //List<KycDetail> details = (List<KycDetail>) response.getBody();
        detail = (KycDetail) response.getBody();
        //detail = details.f.stream().filter(de -> de.tckn.equals(tckn)).findFirst();
        return  detail;
    }
}
