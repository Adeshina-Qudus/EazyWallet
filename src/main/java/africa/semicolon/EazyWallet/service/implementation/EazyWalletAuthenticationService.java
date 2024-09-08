package africa.semicolon.EazyWallet.service.implementation;

import africa.semicolon.EazyWallet.data.models.User;
import africa.semicolon.EazyWallet.data.repository.UserRepository;
import africa.semicolon.EazyWallet.dtos.request.RegistrationRequest;
import africa.semicolon.EazyWallet.dtos.response.RegistrationResponse;
import africa.semicolon.EazyWallet.exception.UserAlreadyExistException;
import africa.semicolon.EazyWallet.service.AuthenticationService;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.EazyWallet.utils.Validation.validateUserDetails;

@Service
public class EazyWalletAuthenticationService implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public RegistrationResponse registration(RegistrationRequest registrationRequest) throws NumberParseException {
        validateUserDetails(registrationRequest);
        userExist(registrationRequest);

        User user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .build();
        userRepository.save(user);


        return new RegistrationResponse("Registration Done");
    }

    private void userExist(RegistrationRequest registrationRequest) {
        if (userExistWithEmail(registrationRequest.getEmail()))
            throw new UserAlreadyExistException(
                    "User With This "+ registrationRequest.getEmail()+" Already Exist");
        if (userExistWithPhoneNumber(registrationRequest.getPhoneNumber()))
            throw new UserAlreadyExistException(
                    "User With This "+ registrationRequest.getPhoneNumber()+" Already Exist");
    }

    private boolean userExistWithPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber).isPresent();
    }

    private boolean userExistWithEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }


}
