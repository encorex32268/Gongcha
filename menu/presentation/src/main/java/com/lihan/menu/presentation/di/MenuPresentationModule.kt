package com.lihan.menu.presentation.di

import com.lihan.menu.presentation.menu.MenuViewModel
import com.lihan.menu.presentation.store.StoreViewModel
import com.lihan.menu.presentation.tea_order.TeaOrderViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val menuPresentationModule = module {
    viewModelOf(::MenuViewModel)
    viewModelOf(::StoreViewModel)
    viewModelOf(::TeaOrderViewModel)
}