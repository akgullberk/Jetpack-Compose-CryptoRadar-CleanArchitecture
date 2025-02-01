package com.example.cryptoradar.crypto.domain // Projenin paket yapısını belirtiyor (Domain katmanı)

// Kripto paraları temsil eden bir veri sınıfı tanımlıyoruz
data class Coin(
    val id: String, // Coin'in benzersiz kimliği (örneğin: "bitcoin", "ethereum")
    val rank: Int, // Piyasa değerine göre sıralama (örn: Bitcoin genellikle 1. sırada olur)
    val name: String, // Coin'in tam adı (örn: "Bitcoin", "Ethereum")
    val symbol: String, // Coin'in kısa sembolü (örn: "BTC", "ETH")
    val marketCapUsd: Double, // Coin'in toplam piyasa değeri (USD cinsinden)
    val priceUsd: Double, // Coin'in güncel fiyatı (USD cinsinden)
    val changePercent24Hr: Double // Son 24 saat içindeki fiyat değişim yüzdesi (örn: -2.5, +5.0)
)
