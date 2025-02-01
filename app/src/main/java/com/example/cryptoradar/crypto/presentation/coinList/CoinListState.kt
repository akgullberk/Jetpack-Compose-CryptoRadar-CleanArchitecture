package com.example.cryptoradar.crypto.presentation.coinList

import androidx.compose.runtime.Immutable
import com.example.cryptoradar.crypto.presentation.models.CoinUi

//Bu @Immutable olarak işaretlenmiş CoinListState veri sınıfı, kripto para listesinin UI durumunu yönetir. Jetpack Compose'da state yönetimi için immutable (değiştirilemez) veri sınıfları önerilir, çünkü bu, recomposition süreçlerinde performansı artırır.
@Immutable
data class CoinListState(
    val isLoading: Boolean = false, // Yükleme durumu, başlangıçta 'false'
    val coins: List<CoinUi> = emptyList(), // Varsayılan olarak boş bir liste
    val selectedCoin: CoinUi? = null // Seçili coin, başlangıçta 'null'
)
