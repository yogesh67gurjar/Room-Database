package com.yogesh.room.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yogesh.room.data.dao.TeacherDao
import com.yogesh.room.data.models.TeacherEntity

@Database(entities = [TeacherEntity::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTeacherDao(): TeacherDao
}