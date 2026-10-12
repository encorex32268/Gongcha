package com.lihan.core.database.di

import androidx.room.Room
import com.lihan.core.database.GongchaDatabase
import com.lihan.core.database.LocalDataSource
import com.lihan.core.database.RoomLocalDataSource
import com.lihan.core.database.migration.MIGRATION_1_2
import com.lihan.core.database.migration.MIGRATION_2_3
import com.lihan.core.database.migration.MIGRATION_3_4
import com.lihan.core.database.migration.MIGRATION_4_5
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            GongchaDatabase::class.java,
            "gongcha.db"
        )
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .addMigrations(MIGRATION_4_5)
            .build()
    }
    single { get<GongchaDatabase>().teaDao }
    single { get<GongchaDatabase>().storeDao }
    single { get<GongchaDatabase>().cartItemDao }
    single { get<GongchaDatabase>().historyDao }

    singleOf(::RoomLocalDataSource).bind<LocalDataSource>()
}
