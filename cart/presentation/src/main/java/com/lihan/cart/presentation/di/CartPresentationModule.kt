package com.lihan.cart.presentation.di

import com.lihan.cart.presentation.CartViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cartPresentationModule = module {
    viewModelOf(::CartViewModel)
}