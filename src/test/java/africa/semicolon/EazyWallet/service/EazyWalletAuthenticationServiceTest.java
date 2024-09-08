package africa.semicolon.EazyWallet.service;

import africa.semicolon.EazyWallet.dtos.request.RegistrationRequest;
import africa.semicolon.EazyWallet.dtos.response.RegistrationResponse;
import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EazyWalletAuthenticationServiceTest {

    @Autowired
    private AuthenticationService authService;

    public RegistrationRequest registerRequest(String firstName, String lastName, String email, String phoneNumber){
        RegistrationRequest registerRequest =
                new RegistrationRequest();
        registerRequest.setFirstName(firstName);
        registerRequest.setLastName(lastName);
        registerRequest.setEmail(email);
        registerRequest.setPhoneNumber(phoneNumber);
        return registerRequest;
    }


    @Test
    public void registrationTest() throws NumberParseException {
        RegistrationRequest registrationRequest =
                registerRequest("Qudus","Lekan","Qudusa55@gmail.com","09079447913");
        RegistrationResponse registrationResponse =
                authService.registration(registrationRequest);
        assertThat(registrationResponse).isNotNull();
    }
}
