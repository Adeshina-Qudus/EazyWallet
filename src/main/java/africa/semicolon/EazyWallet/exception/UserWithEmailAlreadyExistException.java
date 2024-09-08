package africa.semicolon.EazyWallet.exception;

public class UserWithEmailAlreadyExistException extends EazyWalletException {
    public UserWithEmailAlreadyExistException(String message) {
        super(message);
    }
}
