package com.lohanna.nekopics.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lohanna.nekopics.db.dao.CatDao
import com.lohanna.nekopics.db.entity.CatEntity

@Database(entities = [CatEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun catDao(): CatDao

}