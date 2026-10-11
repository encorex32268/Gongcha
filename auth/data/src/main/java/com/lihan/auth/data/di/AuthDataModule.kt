package com.lihan.auth.data.di

import com.lihan.auth.data.AuthRepositoryImpl
import com.lihan.auth.domain.AuthRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module{
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}