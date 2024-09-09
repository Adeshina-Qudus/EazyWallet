package africa.semicolon.EazyWallet.services;

import africa.semicolon.EazyWallet.data.models.Transaction;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
}
