package com.example.cryptoradar.crypto.domain

import com.example.cryptoradar.core.domain.util.NetworkError
import com.example.cryptoradar.core.domain.util.Result

// CoinDataSource adında bir interface tanımlıyoruz.
// Bu interface, kripto paralarla ilgili veri kaynağını temsil eder.
interface CoinDataSource {

    // getCoins fonksiyonu, ağ (network) üzerinden kripto para listesini almak için kullanılır.
    // suspend anahtar kelimesi, fonksiyonun asenkron olarak çalışacağını belirtir. Yani, çağrıldığı zaman
    // işlemin tamamlanmasını bekleyebiliriz (Coroutine kullanılarak çağrılmalıdır).
    // Fonksiyon, bir Result tipi döndürür:
    // - Başarı durumunda: List<Coin> (Coin nesnelerinin bir listesi)
    // - Hata durumunda: NetworkError (Ağ hatası türlerinden biri)
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}