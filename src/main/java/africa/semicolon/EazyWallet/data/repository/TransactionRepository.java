package africa.semicolon.EazyWallet.data.repository;

import africa.semicolon.EazyWallet.data.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
