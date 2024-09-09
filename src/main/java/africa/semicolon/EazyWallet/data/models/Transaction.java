package africa.semicolon.EazyWallet.data.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter

public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private Status status ;
    private String paidAt;
    @ManyToOne
    private Wallet wallet;
}
