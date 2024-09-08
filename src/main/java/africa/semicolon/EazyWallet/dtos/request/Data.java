package africa.semicolon.EazyWallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Data {

    private String status;
    private String paid_at;
    private BigDecimal amount;

}
