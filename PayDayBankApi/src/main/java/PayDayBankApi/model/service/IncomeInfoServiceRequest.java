package PayDayBankApi.model.service;

import javax.validation.constraints.NotNull;

public class IncomeInfoServiceRequest
{
    public IncomeInfoServiceRequest(@NotNull String tckn) {
        this.tckn = tckn;
    }

    @NotNull
    public String tckn;
}