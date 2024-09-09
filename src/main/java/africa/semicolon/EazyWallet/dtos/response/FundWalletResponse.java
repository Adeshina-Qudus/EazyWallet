package africa.semicolon.EazyWallet.dtos.response;

import africa.semicolon.EazyWallet.data.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FundWalletResponse {

    private Transaction transactionDetails;
    private BigDecimal walletBalance;
}
