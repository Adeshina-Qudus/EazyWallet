package africa.semicolon.EazyWallet.controller;


import africa.semicolon.EazyWallet.dtos.request.LoginRequest;
import africa.semicolon.EazyWallet.dtos.request.RegistrationRequest;
import africa.semicolon.EazyWallet.dtos.response.LoginResponse;
import africa.semicolon.EazyWallet.dtos.response.RegistrationResponse;
import africa.semicolon.EazyWallet.exception.EazyWalletException;
import africa.semicolon.EazyWallet.services.AuthenticationService;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {


    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("registration")
    public ResponseEntity<?>  registration(@RequestBody RegistrationRequest registrationRequest) {

        try{
            RegistrationResponse registrationResponse =
                    authenticationService.registration(registrationRequest);
            return ResponseEntity.ok().body(registrationResponse);
        } catch (NumberParseException | EazyWalletException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            LoginResponse loginResponse =
                    authenticationService.login(loginRequest);
            return ResponseEntity.ok().body(loginResponse);
        }catch (EazyWalletException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
