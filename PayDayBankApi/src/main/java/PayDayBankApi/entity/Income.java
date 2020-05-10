package PayDayBankApi.entity;

import org.springframework.data.annotation.Id;

public class Income {
    @Id
    private String id;
    public String tckn;
    public Double income;
}
