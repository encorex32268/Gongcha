package com.lihan.gongcha.di

import com.lihan.gongcha.GongchaApp
import com.lihan.gongcha.MainViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
    single{
        (androidApplication() as GongchaApp).applicationScope
    }.bind<CoroutineScope>()
}