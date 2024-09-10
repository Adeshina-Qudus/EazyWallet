package africa.semicolon.EazyWallet.exception;

public class WalletDoesntExistException extends EazyWalletException {
    public WalletDoesntExistException(String walletNotFound) {
        super(walletNotFound);
    }
}
