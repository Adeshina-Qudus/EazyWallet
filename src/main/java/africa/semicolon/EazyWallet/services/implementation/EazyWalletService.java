package africa.semicolon.EazyWallet.services.implementation;


import africa.semicolon.EazyWallet.data.models.Transaction;
import africa.semicolon.EazyWallet.data.models.User;
import africa.semicolon.EazyWallet.data.models.Wallet;
import africa.semicolon.EazyWallet.data.repository.WalletRepository;
import africa.semicolon.EazyWallet.dtos.request.FundWalletRequest;
import africa.semicolon.EazyWallet.dtos.request.InitializeTransactionRequest;
import africa.semicolon.EazyWallet.dtos.request.SetUpWalletRequest;
import africa.semicolon.EazyWallet.dtos.response.FundWalletResponse;
import africa.semicolon.EazyWallet.dtos.response.InitializeTransactionResponse;
import africa.semicolon.EazyWallet.dtos.response.VerifyTransactionResponse;
import africa.semicolon.EazyWallet.dtos.response.CheckBalanceResponse;
import africa.semicolon.EazyWallet.exception.IncorrectPasswordException;
import africa.semicolon.EazyWallet.exception.WalletAlreadyExistException;
import africa.semicolon.EazyWallet.services.AuthenticationService;
import africa.semicolon.EazyWallet.services.PayStackService;
import africa.semicolon.EazyWallet.services.TransactionService;
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

    @Autowired
    private TransactionService transactionService;

    @Override
    public Wallet setUpWallet(SetUpWalletRequest setUpWalletRequest) {
        walletExist(setUpWalletRequest);

            Wallet wallet = Wallet.builder()

                        .walletAccountName(setUpWalletRequest.getFullName())
                        .walletAccountNumber(setUpWalletRequest.getAccountNumber())
                        .balance(BigDecimal.valueOf(0))
                        .pin(setUpWalletRequest.getPin())
                        .build();

            walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public FundWalletResponse fundWallet(FundWalletRequest fundWalletRequest) {
        User user =  authService.findUserByAccountNumber(fundWalletRequest.getAccountNumber());
        if (! user.getWallet().getPin().equals(
                fundWalletRequest.getPin())) throw new IncorrectPasswordException("Incorrect Pin");
        InitializeTransactionRequest initializeTransactionRequest =
                transactionService.setUpTransactionRequest(user.getEmail(),fundWalletRequest);
        InitializeTransactionResponse response = paystackService.initializeTransaction(initializeTransactionRequest);
        VerifyTransactionResponse verifyTransactionResponse =
                paystackService.verifyTransaction(response.getData().getReference());
        Transaction transaction = transactionService.buildTransaction(user, verifyTransactionResponse,fundWalletRequest.getAmount());
        walletRepository.save(user.getWallet());
        return new FundWalletResponse(transaction,user.getWallet().getBalance());
    }

    @Override
    public CheckBalanceResponse checkBalance(Long userId) {
        User user = authService.findUserById(userId);
        return new CheckBalanceResponse(user.getWallet().getBalance());
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
