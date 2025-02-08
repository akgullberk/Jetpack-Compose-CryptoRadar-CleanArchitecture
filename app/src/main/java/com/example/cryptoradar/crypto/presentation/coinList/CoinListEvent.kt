package com.example.cryptoradar.crypto.presentation.coinList

import com.example.cryptoradar.core.domain.util.NetworkError

// 'Error' data sınıfı tanımlanıyor. Bu sınıf, ağ hatalarını temsil eder.
// NetworkError, hata mesajını içerecek olan veri sınıfıdır.
// CoinListEvent.Error, bu tür hataların bir örneğini taşır
sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}