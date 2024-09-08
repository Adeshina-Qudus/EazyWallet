package africa.semicolon.EazyWallet.dtos.request;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class InitializeTransactionRequest {

    private BigDecimal amount;
    private String email;

}
