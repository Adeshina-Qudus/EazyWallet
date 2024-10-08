package africa.semicolon.EazyWallet.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class RegistrationResponse {

    private String message;
    private String accountNumber;
    private BigDecimal balance;

}
