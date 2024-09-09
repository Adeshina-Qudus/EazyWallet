package africa.semicolon.EazyWallet.services.implementation;

import africa.semicolon.EazyWallet.data.models.Status;
import africa.semicolon.EazyWallet.data.models.Transaction;
import africa.semicolon.EazyWallet.data.models.User;
import africa.semicolon.EazyWallet.data.repository.TransactionRepository;
import africa.semicolon.EazyWallet.dtos.request.FundWalletRequest;
import africa.semicolon.EazyWallet.dtos.request.InitializeTransactionRequest;
import africa.semicolon.EazyWallet.dtos.response.VerifyTransactionResponse;
import africa.semicolon.EazyWallet.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class EazyWalletTransactionService implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public Transaction buildTransaction(User user, VerifyTransactionResponse
            verifyTransactionResponse, BigDecimal amount) {

        Transaction transaction = new Transaction();
        transaction.setUserId(user.getId());
        transaction.setWallet(user.getWallet());
        transaction.setAmount(amount);
        if (verifyTransactionResponse.getData().getStatus().equals("success")){
            updatingTransactions(transaction, verifyTransactionResponse, user);
        }else {
            transaction.setStatus(Status.FAILED);
        }
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public InitializeTransactionRequest setUpTransactionRequest(String email, FundWalletRequest fundWalletRequest) {
        InitializeTransactionRequest initializeTransactionRequest =
                new InitializeTransactionRequest();
        initializeTransactionRequest.setEmail(email);
        initializeTransactionRequest.setAmount(
                fundWalletRequest.getAmount().
                        multiply(BigDecimal.valueOf(100)));
        return initializeTransactionRequest;
    }

    private static void updatingTransactions(Transaction transaction, VerifyTransactionResponse verifyTransactionResponse, User user) {
        transaction.setPaidAt(verifyTransactionResponse.getData().getPaid_at());
        transaction.setStatus(Status.SUCCESSFUL);
        user.getWallet().setBalance(user.getWallet().getBalance().add(verifyTransactionResponse.getData().getAmount()));
    }
}
