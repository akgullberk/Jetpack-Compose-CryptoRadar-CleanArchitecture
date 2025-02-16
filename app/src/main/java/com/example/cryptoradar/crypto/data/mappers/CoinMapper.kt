package com.example.cryptoradar.crypto.data.mappers

import com.example.cryptoradar.crypto.data.networking.dto.CoinDto
import com.example.cryptoradar.crypto.data.networking.dto.CoinPriceDto
import com.example.cryptoradar.crypto.domain.Coin
import com.example.cryptoradar.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr ?: 0.0
    )
}

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.of(ZoneId.systemDefault().toString()))
    )
}