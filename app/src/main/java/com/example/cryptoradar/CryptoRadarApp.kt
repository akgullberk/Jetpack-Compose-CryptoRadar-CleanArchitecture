package com.example.cryptoradar

import android.app.Application
import com.example.cryptoradar.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoRadarApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoRadarApp)
            androidLogger()

            modules(appModule)
        }
    }
}