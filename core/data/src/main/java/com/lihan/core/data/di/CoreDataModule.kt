package com.lihan.core.data.di

import com.lihan.core.data.DefaultAppDataStore
import com.lihan.core.data.OfflineFirstCartRepository
import com.lihan.core.data.auth.AuthRepositoryImpl
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.core.domain.cart.CartRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    singleOf(::DefaultAppDataStore) { bind<AppDataStore>() }
    singleOf(::OfflineFirstCartRepository) { bind<CartRepository>() }
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}
