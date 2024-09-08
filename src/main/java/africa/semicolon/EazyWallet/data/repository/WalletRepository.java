package africa.semicolon.EazyWallet.data.repository;

import africa.semicolon.EazyWallet.data.models.User;
import africa.semicolon.EazyWallet.data.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {


    Optional<Wallet> findWalletByWalletAccountNumber(String accountNumber);
}
