package com.lihan.menu.data.di

import com.lihan.menu.data.MenuRepositoryImpl
import com.lihan.menu.domain.MenuRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val menuDataModule = module {
    singleOf(::MenuRepositoryImpl).bind<MenuRepository>()
}