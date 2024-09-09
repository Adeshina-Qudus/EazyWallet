package africa.semicolon.EazyWallet.services.implementation;

import africa.semicolon.EazyWallet.data.models.User;
import africa.semicolon.EazyWallet.data.models.Wallet;
import africa.semicolon.EazyWallet.data.repository.UserRepository;
import africa.semicolon.EazyWallet.dtos.request.LoginRequest;
import africa.semicolon.EazyWallet.dtos.request.RegistrationRequest;
import africa.semicolon.EazyWallet.dtos.request.SetUpWalletRequest;
import africa.semicolon.EazyWallet.dtos.response.LoginResponse;
import africa.semicolon.EazyWallet.dtos.response.RegistrationResponse;
import africa.semicolon.EazyWallet.exception.*;
import africa.semicolon.EazyWallet.services.AuthenticationService;
import africa.semicolon.EazyWallet.services.WalletService;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static africa.semicolon.EazyWallet.utils.Validation.validateUserDetails;

@Service
public class EazyWalletAuthenticationService implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private WalletService walletService;

    @Value("${spring.security.oauth2.authorizationserver.endpoint.token-uri}")
    private String url;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.grant-type}")
    private String grantType;
    @Value("${keycloak.username}")
    private String username;
    @Value("${keycloak.password}")
    private String password;

    @Override
    public RegistrationResponse registration(RegistrationRequest registrationRequest) throws NumberParseException {
        validateUserDetails(registrationRequest);
        userExist(registrationRequest);
        SetUpWalletRequest setUpWalletRequest = getInfoToSetUpWallet(registrationRequest.getPhoneNumber(),
                registrationRequest.getFirstName()+" "+registrationRequest.getLastName(),
                registrationRequest.getWalletPin());
        Wallet wallet = walletService.setUpWallet(setUpWalletRequest);
        User user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .password(registrationRequest.getPassword())
                .wallet(wallet)
                .build();

        userRepository.save(user);

        return new RegistrationResponse("Registration Done , wallet is active your account number is "
                +wallet.getWalletAccountNumber()+" your balance "+ wallet.getBalance(), wallet.getWalletAccountNumber(),
                wallet.getBalance());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Optional<User> user = userRepository.findUserByEmail(loginRequest.getEmail());
        if (user.isEmpty()){
            throw new UserDoesntExistException("Account With this mail {"+loginRequest.getEmail()+"} doesn't exist ");
        }
        if (! loginRequest.getPassword().equals(user.get().getPassword())){
            throw new IncorrectPasswordException("Incorrect password");
        }
        String accessTokenValue = accessToken();
        String refreshTokenValue = refreshToken();
        return new LoginResponse("you're in ",accessTokenValue,refreshTokenValue);
    }

    @Override
    public User findUserByAccountNumber(String accountNumber) {
        if (userRepository.findUserByPhoneNumber(accountNumber).isEmpty()){
            throw new UserDoesntExistException("User Doesn't Exist");
        }
        return userRepository.findUserByPhoneNumber(accountNumber).get();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    private String accessToken() {
        Map responseMap = getToken();
        return responseMap.get("access_token").toString();
    }

    private String refreshToken(){
        Map responseMap = getToken();
        return responseMap.get("refresh_token").toString();
    }
    private Map getToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("username", username);
        map.add("password", password);
        map.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        return responseEntity.getBody();
    }

    private SetUpWalletRequest getInfoToSetUpWallet(String phoneNumber, String fullName, String pin) {
        SetUpWalletRequest setUpWalletRequest  = new SetUpWalletRequest();
        setUpWalletRequest.setFullName(fullName);
        setUpWalletRequest.setAccountNumber(phoneNumber);
        setUpWalletRequest.setPin(pin);
        return setUpWalletRequest;
    }

    private void userExist(RegistrationRequest registrationRequest) {
        if (userExistWithEmail(registrationRequest.getEmail()))
            throw new UserWithEmailAlreadyExistException(
                    "User With This "+ registrationRequest.getEmail()+" Already Exist");
        if (userExistWithPhoneNumber(registrationRequest.getPhoneNumber()))
            throw new UserWithPhoneNumberAlreadyExistException(
                    "User With This "+ registrationRequest.getPhoneNumber()+" Already Exist");
    }

    private boolean userExistWithPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber).isPresent();
    }

    private boolean userExistWithEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }


}
