package africa.semicolon.EazyWallet.services.implementation;

import africa.semicolon.EazyWallet.config.PaystackConfig;
import africa.semicolon.EazyWallet.dtos.request.InitializeTransactionRequest;
import africa.semicolon.EazyWallet.dtos.response.InitializeTransactionResponse;
import africa.semicolon.EazyWallet.dtos.response.VerifyTransactionResponse;
import africa.semicolon.EazyWallet.services.PayStackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EazyWalletPayStackService implements PayStackService {

    @Autowired
    private PaystackConfig paystackConfig;


    @Override
    public InitializeTransactionResponse initializeTransfer(InitializeTransactionRequest initializeTransactionRequest) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<InitializeTransactionRequest> transactionRequest =
                buildInitializer(initializeTransactionRequest);
        ResponseEntity<InitializeTransactionResponse> response = restTemplate.postForEntity(
                paystackConfig.getPaystackInitializeUrl(),
                transactionRequest, InitializeTransactionResponse.class);
        System.out.println(response.getBody().getData().getAuthorizationUrl());
        return response.getBody();
    }

    @Override
    public VerifyTransactionResponse verifyTransaction(String reference) {
        return null;
    }

    private HttpEntity<InitializeTransactionRequest> buildInitializer(InitializeTransactionRequest
                                                                              initializeTransactionRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+paystackConfig.getPaystackApiKey());
        return new HttpEntity<>(initializeTransactionRequest,headers);

    }
}
