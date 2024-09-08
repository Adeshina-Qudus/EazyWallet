package africa.semicolon.EazyWallet.dtos.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String pin;
    private String password;
}
