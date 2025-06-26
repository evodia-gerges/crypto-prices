package com.example.cryptoprices.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        get<Context>().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
}