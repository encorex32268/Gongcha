package com.lihan.gongcha

import android.app.Application
import com.lihan.core.data.di.coreDataModule
import com.lihan.core.database.di.coreDatabaseModule
import com.lihan.core.network.di.networkModule
import com.lihan.menu.di.menuPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GongchaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GongchaApp)
            modules(
                coreDataModule,
                coreDatabaseModule,
                networkModule,
                menuPresentationModule
            )
        }
    }
}
