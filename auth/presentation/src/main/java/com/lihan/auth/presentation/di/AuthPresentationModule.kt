package com.lihan.auth.presentation.di

import com.lihan.auth.presentation.login.LoginViewModel
import com.lihan.auth.presentation.manager.GoogleAuthManager
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    singleOf(::GoogleAuthManager)
    viewModelOf(::LoginViewModel)
}