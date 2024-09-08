package africa.semicolon.EazyWallet.services;

import africa.semicolon.EazyWallet.dtos.request.RegistrationRequest;
import africa.semicolon.EazyWallet.dtos.response.RegistrationResponse;
import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authService;

    public RegistrationRequest registerRequest(String firstName, String lastName,
                                               String email, String phoneNumber,
                                               String password,String pin){
        RegistrationRequest registerRequest =
                new RegistrationRequest();
        registerRequest.setFirstName(firstName);
        registerRequest.setLastName(lastName);
        registerRequest.setEmail(email);
        registerRequest.setPhoneNumber(phoneNumber);
        registerRequest.setPassword(password);
        registerRequest.setPin(pin);
        return registerRequest;
    }


    @Test
    public void registrationTest() throws NumberParseException {
        RegistrationRequest registrationRequest =
                registerRequest("Qudus","Lekan","Qudusa55@gmail.com",
                        "09079447913","1234","lonly at the top");
        RegistrationResponse registrationResponse =
                authService. registration(registrationRequest);
        assertThat(registrationResponse).isNotNull();
    }
}
