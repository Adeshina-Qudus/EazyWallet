package africa.semicolon.EazyWallet.services;

import africa.semicolon.EazyWallet.data.models.Wallet;
import africa.semicolon.EazyWallet.dtos.request.FundWalletRequest;
import africa.semicolon.EazyWallet.dtos.request.SetUpWalletRequest;
import africa.semicolon.EazyWallet.dtos.response.FundWalletResponse;


public interface WalletService {

    Wallet setUpWallet(SetUpWalletRequest setUpWalletRequest);

    FundWalletResponse fundWallet(FundWalletRequest fundWalletRequest);
}
