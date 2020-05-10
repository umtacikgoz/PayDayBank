package PayDayBankApi.service;

import PayDayBankApi.model.dto.KycDetail;

public interface KycDetailInterface {
    KycDetail getDetail(String tckn);
}
