package africa.semicolon.EazyWallet.services;

import africa.semicolon.EazyWallet.dtos.request.LoginRequest;
import africa.semicolon.EazyWallet.dtos.request.RegistrationRequest;
import africa.semicolon.EazyWallet.dtos.response.LoginResponse;
import africa.semicolon.EazyWallet.dtos.response.RegistrationResponse;
import africa.semicolon.EazyWallet.exception.*;
import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        registerRequest.setWalletPin(pin);
        return registerRequest;
    }


    public LoginRequest loginRequest(String email , String password){
        LoginRequest loginRequest =
                new LoginRequest();
            loginRequest.setEmail(email);
            loginRequest.setPassword(password);
        return loginRequest;
    }


    @Test
    public void registrationTest() throws NumberParseException {
        RegistrationRequest registrationRequest =
                registerRequest("Qudus","Lekan","qudusa55@gmail.com",
                        "09079447913","Iniestajnr1$","1234");
        RegistrationResponse registrationResponse =
                authService. registration(registrationRequest);
        assertThat(registrationResponse).isNotNull();
    }
    @Test
    public void registrationWithWrongPasswordFormatTest() throws NumberParseException {
        RegistrationRequest registrationRequest =
                registerRequest("Qudus","Lekan","qudusa55@gmail.com",
                        "09079447913","Iniestajnr","1234");
        assertThrows(InvalidDetailsException.class, ()->
                       authService. registration(registrationRequest));
    }

    @Test
    public void cannotCompleteRegistrationWithExistingUserEmailTest() throws NumberParseException {
        RegistrationRequest registrationRequest =
                registerRequest("ayo", "emele" ,"qudusa55@gmail.com",
                        "08135347913","Iniestajnr1$","1234");
        assertThrows(UserWithEmailAlreadyExistException.class,()->
                authService.registration(registrationRequest));
    }

    @Test
    public void cannotCompleteRegistrationWithExistingUserPhoneNumberTest() throws NumberParseException {
        RegistrationRequest registrationRequest =
                registerRequest("ayo", "emele" ,"qudusa355@gmail.com",
                        "09079447913","EmeleJnr1$","1234");
        assertThrows(UserWithPhoneNumberAlreadyExistException.class,()->
                authService.registration(registrationRequest));
    }

    @Test
    public void loginTest(){
        LoginRequest loginRequest =
                loginRequest("qudusa55@gmail.com","Iniestajnr1$");
        LoginResponse loginResponse = authService.login(loginRequest);
        assertThat(loginResponse).isNotNull();
    }

    @Test
    public void loginWithWrongEmailTest(){
        LoginRequest loginRequest=
                loginRequest("qudusa5@gmail.com","Iniestajnr1$");
        assertThrows(UserDoesntExistException.class, ()-> authService.login(loginRequest));
    }

    @Test
    public void loginWithWrongPasswordTest(){
        LoginRequest loginRequest=
                loginRequest("qudusa55@gmail.com","Iniestajnr1");
        assertThrows(IncorrectPasswordException.class, ()-> authService.login(loginRequest));
    }
}
