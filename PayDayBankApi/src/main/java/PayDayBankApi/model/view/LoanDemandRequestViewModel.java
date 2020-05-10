package PayDayBankApi.model.view;

import javax.validation.constraints.NotNull;

public class LoanDemandRequestViewModel {

        @NotNull
        public String tckn;
        @NotNull
        public int periodOfMonths;
        @NotNull
        public Double requestedAmount;
    }