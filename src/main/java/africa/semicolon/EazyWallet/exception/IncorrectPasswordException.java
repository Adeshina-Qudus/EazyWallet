package africa.semicolon.EazyWallet.exception;

public class IncorrectPasswordException extends EazyWalletException {
    public IncorrectPasswordException(String incorrectPassword) {
        super(incorrectPassword);
    }
}
