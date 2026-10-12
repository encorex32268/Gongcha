package com.lihan.core.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `CartItemEntity` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT,
                `teaName` TEXT NOT NULL,
                `storeName` TEXT NOT NULL,
                `size` TEXT NOT NULL,
                `toppings` TEXT NOT NULL,
                `sugar` TEXT NOT NULL,
                `ice` TEXT NOT NULL,
                `count` INTEGER NOT NULL,
                `price` INTEGER NOT NULL
            )
        """.trimIndent())
    }
}

val MIGRATION_2_3 = object : Migration(2,3){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE `CartItemEntity` ADD COLUMN `teaId` TEXT NOT NULL DEFAULT ''")

        db.execSQL("ALTER TABLE `CartItemEntity` ADD COLUMN `imageUrl` TEXT")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `histories` (
                `id` TEXT NOT NULL,
                `userId` TEXT NOT NULL,
                `createAt` INTEGER NOT NULL,
                `status` TEXT NOT NULL,
                `orderCode` TEXT NOT NULL,
                `totalPrice` INTEGER NOT NULL,
                `teas` TEXT NOT NULL,
                PRIMARY KEY(`id`)
            )
        """.trimIndent())
    }
}