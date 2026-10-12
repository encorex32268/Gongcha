package com.lihan.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lihan.core.database.converter.Converters
import com.lihan.core.database.converter.ListStringConverters
import com.lihan.core.database.dao.CartItemDao
import com.lihan.core.database.dao.HistoryDao
import com.lihan.core.database.dao.StoreDao
import com.lihan.core.database.dao.TeaDao
import com.lihan.core.database.entity.CartItemEntity
import com.lihan.core.database.entity.HistoryEntity
import com.lihan.core.database.entity.StoreEntity
import com.lihan.core.database.entity.TeaEntity

@Database(
    entities = [
        TeaEntity::class,
        StoreEntity::class,
        CartItemEntity::class,
        HistoryEntity::class
    ],
    version = 5,
    exportSchema = true
)
@TypeConverters(
    value = [Converters::class , ListStringConverters::class]
)
abstract class GongchaDatabase : RoomDatabase() {
    abstract val teaDao: TeaDao
    abstract val storeDao: StoreDao
    abstract val cartItemDao: CartItemDao
    abstract val historyDao: HistoryDao
}
