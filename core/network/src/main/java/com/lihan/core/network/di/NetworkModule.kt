package com.lihan.core.network.di

import com.lihan.core.network.FirebaseRemoteDataSource
import com.lihan.core.network.RemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::FirebaseRemoteDataSource).bind<RemoteDataSource>()
}