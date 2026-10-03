package com.lihan.core.database.di

import androidx.room.Room
import com.lihan.core.database.GongchaDatabase
import com.lihan.core.database.LocalDataSource
import com.lihan.core.database.RoomLocalDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            GongchaDatabase::class.java,
            "gongcha.db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
    single { get<GongchaDatabase>().teaDao }
    single { get<GongchaDatabase>().storeDao }

    singleOf(::RoomLocalDataSource).bind<LocalDataSource>()
}
