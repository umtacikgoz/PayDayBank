package PayDayBankApi.model.queue;

import java.io.Serializable;
import java.util.Date;

public class LoanDemand implements Serializable {

    public String email;
    public Double income;
    public Double requestedLoan;
    public Double interestRate;
    public Double previouslyGrantedLoan;
    public Date lastEmployerStartDate;
}