package africa.semicolon.EazyWallet.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {


    private String message;
    private String accessToken;
    private String refreshToken;


}
