package africa.semicolon.EazyWallet.services.implementation;


import africa.semicolon.EazyWallet.data.models.Wallet;
import africa.semicolon.EazyWallet.data.repository.WalletRepository;
import africa.semicolon.EazyWallet.dtos.request.SetUpWalletRequest;
import africa.semicolon.EazyWallet.exception.UserAlreadyExistException;
import africa.semicolon.EazyWallet.exception.WalletAlreadyExistException;
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
        walletExist(setUpWalletRequest);

            Wallet wallet = Wallet.builder()
                        .walletAccountName(setUpWalletRequest.getFullName())
                        .walletAccountNumber(setUpWalletRequest.getAccountNumber())
                        .balance(BigDecimal.valueOf(0.0))
                        .pin(setUpWalletRequest.getPin())
                        .build();

            walletRepository.save(wallet);
        return wallet;
    }

    private void walletExist(SetUpWalletRequest setUpWalletRequest) {
        if (walletWithPhoneNumberExist(setUpWalletRequest.getAccountNumber()))
            throw new WalletAlreadyExistException(
                    "Wallet With This "+ setUpWalletRequest.getAccountNumber()+" Account Number Already Exist");
    }

    private boolean walletWithPhoneNumberExist(String accountNumber) {
        return walletRepository.findWalletByWalletAccountNumber(accountNumber).isPresent();
    }
}
