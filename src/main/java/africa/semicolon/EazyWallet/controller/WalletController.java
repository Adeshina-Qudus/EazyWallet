package africa.semicolon.EazyWallet.controller;

import africa.semicolon.EazyWallet.dtos.request.FundWalletRequest;
import africa.semicolon.EazyWallet.dtos.response.CheckBalanceResponse;
import africa.semicolon.EazyWallet.dtos.response.FundWalletResponse;
import africa.semicolon.EazyWallet.exception.EazyWalletException;
import africa.semicolon.EazyWallet.services.WalletService;
import africa.semicolon.EazyWallet.services.implementation.EazyWalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/wallet/")
public class WalletController {


    @Autowired
    private WalletService walletService;


    @PostMapping("fundWallet")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<?> fundWallet(@RequestBody FundWalletRequest fundWalletRequest){
        try {
            FundWalletResponse fundWalletResponse =
                    walletService.fundWallet(fundWalletRequest);
            return ResponseEntity.ok().body(fundWalletResponse);
        }catch (EazyWalletException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("checkBalance")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<?> checkBalance(@PathVariable("id") Long id ){
        try{
            CheckBalanceResponse checkBalanceResponse =
                    walletService.checkBalance(id);
            return ResponseEntity.ok().body(checkBalanceResponse);
        }catch (EazyWalletException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
