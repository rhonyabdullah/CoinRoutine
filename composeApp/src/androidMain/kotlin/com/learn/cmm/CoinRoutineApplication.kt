package com.learn.cmm

import android.app.Application
import com.learn.cmm.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent

class CoinRoutineApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CoinRoutineApplication)
        }
    }
}
