package PayDayBankApi.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer {

    @Id
    private String id;
    public String tckn;
    public String name;
    public String surname;
    public String customerId;
    public String customerPass;
}
