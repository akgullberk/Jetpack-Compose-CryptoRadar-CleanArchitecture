package com.example.cryptoradar.crypto.data.networking

import com.example.cryptoradar.core.data.networking.constructUrl
import com.example.cryptoradar.core.data.networking.safeCall
import com.example.cryptoradar.core.domain.util.NetworkError
import com.example.cryptoradar.core.domain.util.Result
import com.example.cryptoradar.core.domain.util.map
import com.example.cryptoradar.crypto.data.mappers.toCoin
import com.example.cryptoradar.crypto.data.mappers.toCoinPrice
import com.example.cryptoradar.crypto.data.networking.dto.CoinHistoryDto
import com.example.cryptoradar.crypto.data.networking.dto.CoinsResponseDto
import com.example.cryptoradar.crypto.domain.Coin
import com.example.cryptoradar.crypto.domain.CoinDataSource
import com.example.cryptoradar.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

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

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice()}
        }
    }
}