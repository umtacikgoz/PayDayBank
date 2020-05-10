package InternalKycApi.entity;

import org.springframework.data.annotation.Id;

public class Detail {
    @Id
    private String id;
    public String tckn;
    public String email;
    public String address;
    public String lastEmployer;
    public String lastEmployerStartDate;
    public Double previouslyGrantedLoan;
}
