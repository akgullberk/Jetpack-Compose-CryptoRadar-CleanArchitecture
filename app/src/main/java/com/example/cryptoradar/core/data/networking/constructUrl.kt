package com.example.cryptoradar.core.data.networking

import com.example.cryptoradar.BuildConfig

fun constructUrl(url: String): String {

    // `when` bloğu ile URL'nin nasıl işleneceğine karar veriyoruz.
    return when {

        // 1. Durum: Eğer `url`, `BuildConfig.BASE_URL` içeriyorsa
        url.contains(BuildConfig.BASE_URL) -> url

        // 2. Durum: Eğer `url`, bir '/' ile başlıyorsa
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)

        // 3. Durum: Diğer tüm durumlar
        else -> BuildConfig.BASE_URL + url
    }
}