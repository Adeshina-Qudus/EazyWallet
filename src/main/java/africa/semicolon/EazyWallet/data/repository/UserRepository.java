package africa.semicolon.EazyWallet.data.repository;

import africa.semicolon.EazyWallet.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findUserByEmail( String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);
}
