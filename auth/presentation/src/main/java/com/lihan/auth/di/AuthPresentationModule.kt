package com.lihan.auth.di

import com.lihan.auth.presentation.code_verify.CodeVerifyViewModel
import com.lihan.auth.presentation.login.LoginViewModel
import com.lihan.auth.presentation.manager.GoogleAuthManager
import com.lihan.auth.presentation.manager.PhoneAuthManager
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    singleOf(::PhoneAuthManager)
    singleOf(::GoogleAuthManager)
    viewModelOf(::LoginViewModel)
    viewModelOf(::CodeVerifyViewModel)
}