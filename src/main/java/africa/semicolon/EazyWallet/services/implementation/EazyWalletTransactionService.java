package africa.semicolon.EazyWallet.services.implementation;

import africa.semicolon.EazyWallet.data.models.Transaction;
import africa.semicolon.EazyWallet.data.repository.TransactionRepository;
import africa.semicolon.EazyWallet.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EazyWalletTransactionService implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
