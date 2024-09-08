package africa.semicolon.EazyWallet.dtos.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
public class SetUpWalletRequest {

    private String fullName;
    private String accountNumber;
    private String pin;
}
