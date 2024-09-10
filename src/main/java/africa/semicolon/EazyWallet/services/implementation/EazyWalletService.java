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
import africa.semicolon.EazyWallet.exception.WalletDoesntExistException;
import africa.semicolon.EazyWallet.services.AuthenticationService;
import africa.semicolon.EazyWallet.services.PayStackService;
import africa.semicolon.EazyWallet.services.TransactionService;
import africa.semicolon.EazyWallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EazyWalletService implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

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
                        .email(setUpWalletRequest.getEmail())
                        .balance(BigDecimal.valueOf(0))
                        .pin(setUpWalletRequest.getPin())
                        .build();

            walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public FundWalletResponse fundWallet(FundWalletRequest fundWalletRequest) {
        Optional<Wallet> wallet =  walletRepository.findWalletByWalletAccountNumber(fundWalletRequest.getAccountNumber());
        checkIfWalletExist(wallet);
        if (wallet.get().getPin().equals(
                fundWalletRequest.getPin())) throw new IncorrectPasswordException("Incorrect Pin");
        InitializeTransactionRequest initializeTransactionRequest =
                transactionService.setUpTransactionRequest(wallet.get().getEmail(),fundWalletRequest);
        InitializeTransactionResponse response = paystackService.initializeTransaction(initializeTransactionRequest);
        VerifyTransactionResponse verifyTransactionResponse =
                paystackService.verifyTransaction(response.getData().getReference());
        Transaction transaction = transactionService.buildTransaction(wallet.get(), verifyTransactionResponse,fundWalletRequest.getAmount());
        walletRepository.save(wallet.get());
        return new FundWalletResponse(transaction,wallet.get().getBalance());
    }

    @Override
    public CheckBalanceResponse checkBalance(Long userId) {
        Optional<Wallet> wallet = walletRepository.findById(userId);
        checkIfWalletExist(wallet);
        return new CheckBalanceResponse(wallet.get().getBalance());
    }

    private static void checkIfWalletExist(Optional<Wallet> wallet) {
        if (wallet.isEmpty()){
            throw new WalletDoesntExistException("Wallet Not Found");
        }
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
