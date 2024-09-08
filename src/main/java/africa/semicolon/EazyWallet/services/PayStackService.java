package africa.semicolon.EazyWallet.services;

import africa.semicolon.EazyWallet.dtos.request.InitializeTransactionRequest;
import africa.semicolon.EazyWallet.dtos.response.InitializeTransactionResponse;
import africa.semicolon.EazyWallet.dtos.response.VerifyTransactionResponse;

public interface PayStackService {

    InitializeTransactionResponse initializeTransfer(InitializeTransactionRequest initializeTransactionRequest);

    VerifyTransactionResponse verifyTransaction(String reference);
}
