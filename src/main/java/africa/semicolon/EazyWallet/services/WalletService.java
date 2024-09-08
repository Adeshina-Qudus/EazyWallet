package africa.semicolon.EazyWallet.services;

import africa.semicolon.EazyWallet.data.models.Wallet;
import africa.semicolon.EazyWallet.dtos.request.SetUpWalletRequest;


public interface WalletService {

    Wallet setUpWallet(SetUpWalletRequest setUpWalletRequest);
}
