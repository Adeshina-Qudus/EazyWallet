package africa.semicolon.EazyWallet.services;

import africa.semicolon.EazyWallet.data.models.Transaction;
import africa.semicolon.EazyWallet.data.models.User;
import africa.semicolon.EazyWallet.data.models.Wallet;
import africa.semicolon.EazyWallet.dtos.request.FundWalletRequest;
import africa.semicolon.EazyWallet.dtos.request.InitializeTransactionRequest;
import africa.semicolon.EazyWallet.dtos.response.VerifyTransactionResponse;

import java.math.BigDecimal;

public interface TransactionService {
    
    Transaction buildTransaction(Wallet wallet, VerifyTransactionResponse verifyTransactionResponse, BigDecimal amount);

    InitializeTransactionRequest setUpTransactionRequest(String email, FundWalletRequest fundWalletRequest);
}
