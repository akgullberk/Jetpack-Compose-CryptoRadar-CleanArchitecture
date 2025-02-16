package com.example.cryptoradar.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.example.cryptoradar.crypto.domain.Coin
import java.util.Locale
import android.icu.text.NumberFormat
import com.example.cryptoradar.core.presentation.util.getDrawableIdForCoin
import com.example.cryptoradar.crypto.presentation.coinDetail.DataPoint

data class CoinUi(
    val id: String, // Coin'in benzersiz kimliği
    val rank: Int, // Coin'in sıralaması (örneğin, 1. sıradaki Bitcoin)
    val name: String, // Coin'in ismi (örneğin, "Bitcoin")
    val symbol: String, // Coin'in sembolü (örneğin, "BTC")
    val marketCapUsd: DisplayableNumber, // Coin'in piyasa değeri (USD cinsinden, formatlanmış)
    val priceUsd: DisplayableNumber, // Coin'in fiyatı (USD cinsinden, formatlanmış)
    val changePercent24Hr: DisplayableNumber, // Coin'in 24 saatlik değişim yüzdesi (formatlanmış)
    val coinPriceHistory: List<DataPoint> = emptyList(),
    @DrawableRes val iconRes: Int // Coin için kullanılacak simge (drawable resource id)
)

data class DisplayableNumber(
    val value: Double, // Değerin ham hali (örneğin, 62828.15)
    val formatted: String // Formatlanmış hali (örneğin, "$ 62,828.15")
)

fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id, // Coin’in ID’si
        name = name, // Coin’in adı
        symbol = symbol, // Coin’in sembolü
        rank = rank, // Coin’in sıralaması
        priceUsd = priceUsd.toDisplayableNumber(), // Coin’in fiyatını formatla
        marketCapUsd = marketCapUsd.toDisplayableNumber(), // Piyasa değerini formatla
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(), // Değişim oranını formatla
        iconRes = getDrawableIdForCoin(symbol) // Coin’in simgesinin drawable kaynak ID’si
    )
}

fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2 // En az 2 ondalık basamağı olacak
        maximumFractionDigits = 2 // En fazla 2 ondalık basamağı olacak
    }
    return DisplayableNumber(
        value = this, // Ham değeri
        formatted = formatter.format(this) // Formatlanmış değeri
    )
}
