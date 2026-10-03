package com.lihan.menu.di

import com.lihan.menu.presentation.menu.MenuViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val menuPresentationModule = module {
    viewModelOf(::MenuViewModel)
}