package africa.semicolon.EazyWallet.services;

import africa.semicolon.EazyWallet.data.models.Wallet;
import africa.semicolon.EazyWallet.dtos.request.SetUpWalletRequest;
import africa.semicolon.EazyWallet.dtos.response.SetUpWalletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    public SetUpWalletRequest setUpWalletRequest(String fullName, String accountNumber , String pin){
        SetUpWalletRequest setUpWalletRequest =
                new SetUpWalletRequest();
                    setUpWalletRequest.setFullName(fullName);
                    setUpWalletRequest.setAccountNumber(accountNumber);
                    setUpWalletRequest.setPin(pin);
        return setUpWalletRequest;
    }

    @Test
    public void setUpWalletTest(){
        SetUpWalletRequest setUpWalletRequest = setUpWalletRequest("Qudus Lekan","09079447913", "1234");
        Wallet setUpWalletResponse =
                walletService.setUpWallet(setUpWalletRequest);
        assertThat(setUpWalletResponse).isNotNull();
    }
}
