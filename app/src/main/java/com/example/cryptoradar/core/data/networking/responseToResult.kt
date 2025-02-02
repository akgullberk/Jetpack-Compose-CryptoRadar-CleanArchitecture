package com.example.cryptoradar.core.data.networking

import com.example.cryptoradar.core.domain.util.NetworkError
import com.example.cryptoradar.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

// `responseToResult` fonksiyonu, bir HTTP yanıtını işleyerek başarılı veya hatalı bir sonuç döndürür.
// Bu fonksiyon asenkron (suspend) olduğu için, başka bir suspend fonksiyon içinde çağrılabilir.
suspend inline fun <reified T> responseToResult(
    response: HttpResponse // HTTP yanıtı
): Result<T, NetworkError> { // Sonuç olarak başarılı veri veya ağ hatası döndürülür.

    // HTTP yanıtının durum koduna göre işlem yapıyoruz.
    return when (response.status.value) {

        // Eğer HTTP yanıt durumu 200 ile 299 arasında ise, bu başarılı bir yanıt anlamına gelir.
        // Yani istek başarılı olmuştur.
        in 200..299 -> {
            try {

                // Yanıt gövdesini (body) `T` türüne dönüştürmeye çalışıyoruz.
                // Dönüştürme başarılıysa, başarılı sonuç döndürülür.
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {

                // Eğer dönüşüm sırasında bir hata meydana gelirse (örneğin, veri tipi uyuşmazlığı),
                // `NetworkError.SERIALIZATION` hatası döndürülür.
                Result.Error(NetworkError.SERIALIZATION)
            }
        }

        // HTTP yanıt durumu 408 ise, bu "Request Timeout" hatasıdır.
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)

        // HTTP yanıt durumu 429 ise, bu "Too Many Requests" hatasıdır.
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)

        // HTTP yanıt durumu 500 ile 599 arasında ise, bu sunucu hatasıdır.
        // Sunucu tarafında bir hata oluştuğunu belirtir.
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)

        // Yukarıdaki durumlar dışında kalan tüm durumlar için, bilinmeyen bir hata olduğunu varsayarız.
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}