package com.yogesh.room.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.yogesh.room.data.models.TeacherEntity

@Dao
interface TeacherDao {
    @Insert
    suspend fun insertTeacher(teacherEntity: TeacherEntity): Long

}