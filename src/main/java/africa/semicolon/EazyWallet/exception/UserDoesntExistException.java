package africa.semicolon.EazyWallet.exception;

public class UserDoesntExistException extends EazyWalletException {
    public UserDoesntExistException(String message) {
        super(message);
    }
}
