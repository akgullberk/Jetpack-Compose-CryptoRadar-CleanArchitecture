package com.example.cryptoradar.di

import com.example.cryptoradar.core.data.networking.HttpClientFactory
import com.example.cryptoradar.crypto.data.networking.RemoteCoinDataSource
import com.example.cryptoradar.crypto.domain.CoinDataSource
import com.example.cryptoradar.crypto.presentation.coinList.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

// Koin modülü tanımlanıyor. Uygulamanın bağımlılık yönetimini bu modül üzerinden yapacağız.
val appModule = module {

    // HTTP istemcisi (client) oluşturuluyor ve bağımlılık olarak sağlanıyor.
    // "single" fonksiyonu, uygulama boyunca tek bir HttpClientFactory örneğinin kullanılmasını sağlar.
    single { HttpClientFactory.create(CIO.create()) }

    // RemoteCoinDataSource sınıfını bağımlılık olarak sağlıyoruz.
    // "singleOf" fonksiyonu, RemoteCoinDataSource'un tek bir örneğini oluşturup kullanmamızı sağlar.
    // "bind<CoinDataSource>()" ile RemoteCoinDataSource'un CoinDataSource arayüzünü implemente ettiğini belirtiyoruz.
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    // CoinListViewModel sınıfını ViewModel olarak kaydediyoruz.
    // "viewModelOf" fonksiyonu ile ViewModel'imizin örneğini Koin üzerinden yönetilebilir hale getiriyoruz.
    viewModelOf(::CoinListViewModel)
}