package com.yogesh.room.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yogesh.room.data.models.TeacherEntity

@Dao
interface TeacherDao {
    @Insert
    suspend fun insertTeacher(teacherEntity: TeacherEntity): Long

    @Update
    suspend fun updateTeacher(teacherEntity: TeacherEntity): Int

    @Delete
    suspend fun deleteTeacher(teacherEntity: TeacherEntity): Int


    @Query("SELECT * from TeacherEntity")
    suspend fun getAllTeachers(): List<TeacherEntity>

}