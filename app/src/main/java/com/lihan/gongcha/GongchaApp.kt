package com.lihan.gongcha

import android.app.Application
import com.lihan.auth.presentation.di.authPresentationModule
import com.lihan.cart.presentation.di.cartPresentationModule
import com.lihan.core.data.di.coreDataModule
import com.lihan.core.database.di.coreDatabaseModule
import com.lihan.core.network.di.networkModule
import com.lihan.gongcha.di.appModule
import com.lihan.history.data.di.historyDataModule
import com.lihan.history.presentation.history.di.historyPresentationModule
import com.lihan.member.presentation.di.memberPresentationModule
import com.lihan.menu.data.di.menuDataModule
import com.lihan.menu.presentation.di.menuPresentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class GongchaApp : Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@GongchaApp)
            modules(
                appModule,
                coreDataModule,
                coreDatabaseModule,
                authPresentationModule,
                networkModule,
                menuDataModule,
                menuPresentationModule,
                cartPresentationModule,
                memberPresentationModule,
                historyDataModule,
                historyPresentationModule
            )
        }
    }
}
