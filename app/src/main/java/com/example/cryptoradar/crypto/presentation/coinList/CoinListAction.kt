package com.example.cryptoradar.crypto.presentation.coinList

import com.example.cryptoradar.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
}