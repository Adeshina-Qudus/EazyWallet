package africa.semicolon.EazyWallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter


public class FundWalletRequest {

    private String accountNumber;
    private String amount;
}
