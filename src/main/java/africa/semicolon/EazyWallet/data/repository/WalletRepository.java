package africa.semicolon.EazyWallet.data.repository;

import africa.semicolon.EazyWallet.data.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {


}
