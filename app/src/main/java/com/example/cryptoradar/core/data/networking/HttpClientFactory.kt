package com.example.cryptoradar.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Singleton (tekil nesne) olarak tanımlanmış bir `object`.
// `HttpClientFactory`, HTTP istemcisi oluşturmak için kullanılır.
object HttpClientFactory {

    // create() fonksiyonu, belirli bir `HttpClientEngine` ile `HttpClient` oluşturur.
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {

            // `Logging` özelliğini yükleyerek HTTP isteklerinin loglanmasını sağlar.
            install(Logging) {
                level = LogLevel.ALL // Tüm HTTP işlemlerini (istek, yanıt, hata vb.) loglar.
                logger = Logger.ANDROID // Log çıktısını Android konsoluna yönlendirir.
            }

            // `ContentNegotiation` eklentisini yükleyerek JSON desteği ekler.
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true // JSON'daki bilinmeyen alanları yoksayarak hata almayı önler.
                    }
                )
            }

            // Varsayılan istek başlıklarını ayarlar.
            defaultRequest {
                contentType(ContentType.Application.Json) // Tüm isteklerde içerik türünü JSON olarak belirler.
            }
        }
    }
}