package africa.semicolon.EazyWallet.services.implementation;


import africa.semicolon.EazyWallet.data.models.Wallet;
import africa.semicolon.EazyWallet.data.repository.WalletRepository;
import africa.semicolon.EazyWallet.dtos.request.SetUpWalletRequest;
import africa.semicolon.EazyWallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EazyWalletService implements WalletService {


    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet setUpWallet(SetUpWalletRequest setUpWalletRequest) {
        Wallet wallet = fillWalletDetails(setUpWalletRequest);
        walletRepository.save(wallet);
        return wallet;
    }

    private static Wallet fillWalletDetails(SetUpWalletRequest setUpWalletRequest) {
        Wallet wallet = new Wallet();
        wallet.setWalletAccountName(setUpWalletRequest.getFullName());
        wallet.setWalletAccountNumber(setUpWalletRequest.getAccountNumber());
        wallet.setBalance(BigDecimal.valueOf(0.0));
        wallet.setPin(setUpWalletRequest.getPin());
        return wallet;
    }
}
