package africa.semicolon.EazyWallet.utils;

import africa.semicolon.EazyWallet.dtos.request.RegistrationRequest;
import africa.semicolon.EazyWallet.exception.EmptyDetailsException;
import africa.semicolon.EazyWallet.exception.InvalidDetailsException;
import africa.semicolon.EazyWallet.exception.InvalidEmailException;
import africa.semicolon.EazyWallet.exception.InvalidPhoneNumberException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;


public class Validation {
    public static void validateUserDetails(RegistrationRequest request) throws NumberParseException {
        phoneNumberValidation(request.getPhoneNumber());
        verifyEmail(request.getEmail());
        verifyPassword(request.getPassword());
    }
    public static boolean isPhoneNumberValid(String contact) throws NumberParseException {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(contact, "IN");
        return phoneNumberUtil.isValidNumber(phoneNumber);
    }

    public static void phoneNumberValidation(String number) throws InvalidPhoneNumberException, NumberParseException {
        if (!isPhoneNumberValid(number)) {
            throw new InvalidPhoneNumberException("invalid phone number");
        }
    }

    public static void verifyEmail(String email) throws InvalidEmailException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidEmailException("email field is empty, kindly provide your email");
        }
        if (!email.matches("[A-z0-9!#$%^&():;.*_~`+{}]+@[a-z]+[.][a-z]{2,3}")){
            throw new InvalidEmailException("email address is not valid");
        }
    }

    public static void verifyPassword(String password) throws EmptyDetailsException, InvalidDetailsException {
        if (password == null|| password.trim().isEmpty()){
            throw new EmptyDetailsException("password field is empty, kindly provide your password");
        }

        if (!password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])(?!.*\\s).{6,}$")) {
            throw new InvalidDetailsException("password is too weak. It must contain letters, numbers, and special characters. The length must be greater than 5.");
        }
    }
}
