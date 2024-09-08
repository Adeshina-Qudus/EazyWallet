package africa.semicolon.EazyWallet.services.implementation;


import africa.semicolon.EazyWallet.data.models.User;
import africa.semicolon.EazyWallet.data.models.Wallet;
import africa.semicolon.EazyWallet.data.repository.WalletRepository;
import africa.semicolon.EazyWallet.dtos.request.FundWalletRequest;
import africa.semicolon.EazyWallet.dtos.request.InitializeTransactionRequest;
import africa.semicolon.EazyWallet.dtos.request.SetUpWalletRequest;
import africa.semicolon.EazyWallet.dtos.response.FundWalletResponse;
import africa.semicolon.EazyWallet.dtos.response.InitializeTransactionResponse;
import africa.semicolon.EazyWallet.dtos.response.VerifyTransactionResponse;
import africa.semicolon.EazyWallet.exception.WalletAlreadyExistException;
import africa.semicolon.EazyWallet.services.AuthenticationService;
import africa.semicolon.EazyWallet.services.PayStackService;
import africa.semicolon.EazyWallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EazyWalletService implements WalletService {


    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private PayStackService paystackService;

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

    @Override
    public FundWalletResponse fundWallet(FundWalletRequest fundWalletRequest) {
        User user =  authService.findUserByAccountNumber(fundWalletRequest.getAccountNumber());
        InitializeTransactionRequest initializeTransactionRequest =
                initializeTransaction(user.getEmail(),fundWalletRequest);
        InitializeTransactionResponse response = paystackService.initializeTransfer(initializeTransactionRequest);
        VerifyTransactionResponse verifyTransactionResponse =
                paystackService.verifyTransaction(response.getData().getReference());
//        if (response.getData().getStatus().equals("success")){
//
//        }

        return null;
    }

    private InitializeTransactionRequest initializeTransaction(String email, FundWalletRequest fundWalletRequest) {
        InitializeTransactionRequest initializeTransactionRequest =
                new InitializeTransactionRequest();
        initializeTransactionRequest.setEmail(email);
        initializeTransactionRequest.setAmount(BigDecimal.valueOf(
                Integer.parseInt(fundWalletRequest.getAmount()))
                .multiply(BigDecimal.valueOf(100)));
        return initializeTransactionRequest;
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
