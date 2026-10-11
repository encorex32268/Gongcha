package com.lihan.gongcha

import android.app.Application
import com.lihan.auth.data.di.authDataModule
import com.lihan.auth.presentation.di.authPresentationModule
import com.lihan.cart.presentation.di.cartPresentationModule
import com.lihan.core.data.di.coreDataModule
import com.lihan.core.database.di.coreDatabaseModule
import com.lihan.core.network.di.networkModule
import com.lihan.gongcha.di.appModule
import com.lihan.member.presentation.di.memberPresentationModule
import com.lihan.menu.data.di.menuDataModule
import com.lihan.menu.presentation.di.menuPresentationModule
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
                appModule,
                coreDataModule,
                coreDatabaseModule,
                authDataModule,
                authPresentationModule,
                networkModule,
                menuDataModule,
                menuPresentationModule,
                cartPresentationModule,
                memberPresentationModule

            )
        }
    }
}
