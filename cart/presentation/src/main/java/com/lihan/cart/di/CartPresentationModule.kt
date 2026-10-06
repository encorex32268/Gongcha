package com.lihan.cart.di

import com.lihan.cart.cart.CartViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cartPresentationModule = module {
    viewModelOf(::CartViewModel)
}