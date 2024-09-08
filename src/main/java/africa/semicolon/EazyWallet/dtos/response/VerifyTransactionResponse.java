package africa.semicolon.EazyWallet.dtos.response;


import africa.semicolon.EazyWallet.dtos.request.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyTransactionResponse {


    private boolean status;
    public Data data;
}
