package com.lihan.menu.di

import com.lihan.menu.menu.MenuViewModel
import com.lihan.menu.store.StoreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val menuPresentationModule = module {
    viewModelOf(::MenuViewModel)
    viewModelOf(::StoreViewModel)
}