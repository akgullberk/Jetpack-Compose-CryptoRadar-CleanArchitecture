package com.example.cryptoradar.crypto.data.networking

import com.example.cryptoradar.core.data.networking.constructUrl
import com.example.cryptoradar.core.data.networking.safeCall
import com.example.cryptoradar.core.domain.util.NetworkError
import com.example.cryptoradar.core.domain.util.Result
import com.example.cryptoradar.core.domain.util.map
import com.example.cryptoradar.crypto.data.mappers.toCoin
import com.example.cryptoradar.crypto.data.networking.dto.CoinsResponseDto
import com.example.cryptoradar.crypto.domain.Coin
import com.example.cryptoradar.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

// RemoteCoinDataSource sınıfı, CoinDataSource arayüzünü (interface) uygular.
// Bu sınıf, uzak bir sunucudan (API) kripto para verilerini almak için kullanılır.
class RemoteCoinDataSource(
    private val httpClient: HttpClient // HTTP isteklerini yapacak olan HttpClient nesnesi
): CoinDataSource { // CoinDataSource arayüzünü uyguluyor.

    // getCoins fonksiyonu, API'den kripto para listesini almak için kullanılır.
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> { // safeCall fonksiyonunu kullanarak API çağrısını güvenli bir şekilde yapıyoruz.
            httpClient.get( // HTTP GET isteği yapılıyor.
                urlString = constructUrl("/assets") //API'den veriyi almak için gerekli URL oluşturuluyor.
            )
        }.map { response -> // API çağrısı başarılı olursa, dönen cevabı işliyoruz.
            response.data.map { it.toCoin() } // API'den gelen veri (CoinsResponseDto) işlenerek Coin nesnelerine dönüştürülüyor.
        }
    }
}