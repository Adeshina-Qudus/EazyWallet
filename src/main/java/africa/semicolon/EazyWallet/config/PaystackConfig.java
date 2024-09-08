package africa.semicolon.EazyWallet.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
public class PaystackConfig {

    @Value("${paystack.api.key}")
    private String paystackApiKey;
    @Value("${paystack.base.url}")
    private String paystackInitializeUrl;
    @Value("${paystack.verify.url}")
    private String paystackVerifyUrl;
}
