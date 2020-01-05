package org.wit.hillfortexplorer.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.wit.hillfortexplorer.models.HillfortModel

@Database(entities = arrayOf(HillfortModel::class), version = 1,  exportSchema = false)
@TypeConverters(HillfortConverters::class)
abstract class Database : RoomDatabase() {

    abstract fun hillfortDao(): HillfortDao
}