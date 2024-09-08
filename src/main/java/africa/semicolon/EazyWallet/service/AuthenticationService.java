package africa.semicolon.EazyWallet.service;

import africa.semicolon.EazyWallet.dtos.request.RegistrationRequest;
import africa.semicolon.EazyWallet.dtos.response.RegistrationResponse;
import com.google.i18n.phonenumbers.NumberParseException;

public interface AuthenticationService {
    RegistrationResponse registration(RegistrationRequest registrationRequest) throws NumberParseException;

}
