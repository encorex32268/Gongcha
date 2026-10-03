package com.lihan.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lihan.core.database.converter.Converters
import com.lihan.core.database.dao.StoreDao
import com.lihan.core.database.dao.TeaDao
import com.lihan.core.database.entity.StoreEntity
import com.lihan.core.database.entity.TeaEntity

@Database(
    entities = [
        TeaEntity::class,
        StoreEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class GongchaDatabase : RoomDatabase() {
    abstract val teaDao: TeaDao
    abstract val storeDao: StoreDao
}
