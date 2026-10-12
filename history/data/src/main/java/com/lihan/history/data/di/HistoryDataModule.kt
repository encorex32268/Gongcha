package com.lihan.history.data.di

import com.lihan.history.data.HistoryRepositoryImpl
import com.lihan.history.domain.HistoryRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val historyDataModule = module {
    singleOf(::HistoryRepositoryImpl).bind<HistoryRepository>()
}