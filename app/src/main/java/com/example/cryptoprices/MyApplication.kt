package com.example.cryptoprices

import android.app.Application
import com.example.cryptoprices.di.appModule
import com.example.cryptoprices.di.data.network.networkModule
import com.example.cryptoprices.di.data.repo.repoModule
import com.example.cryptoprices.di.domain.usecase.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                appModule,
                networkModule,
                repoModule,
                useCaseModule
            )
        }
    }
}