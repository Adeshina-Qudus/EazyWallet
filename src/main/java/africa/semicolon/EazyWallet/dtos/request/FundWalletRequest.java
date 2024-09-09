package africa.semicolon.EazyWallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter


public class FundWalletRequest {

    private String accountNumber;
    private BigDecimal amount;
    private String pin;
}
