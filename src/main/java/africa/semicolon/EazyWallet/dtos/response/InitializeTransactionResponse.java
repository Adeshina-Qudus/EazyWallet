package africa.semicolon.EazyWallet.dtos.response;

import africa.semicolon.EazyWallet.dtos.request.Data;
import africa.semicolon.EazyWallet.dtos.request.PaystackTransactionDetails;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitializeTransactionResponse {

    private boolean status;
    private PaystackTransactionDetails data;
}
