package com.yogesh.room.data.repository

import com.yogesh.room.data.database.AppDatabase
import com.yogesh.room.data.models.TeacherEntity
import com.yogesh.room.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SchoolRepository @Inject constructor(private val appDatabase: AppDatabase) {
    suspend fun insertTeacher(teacherEntity: TeacherEntity): Flow<Resource<Long>> = flow {
        emit(Resource.loading())
        emit(Resource.success(appDatabase.getTeacherDao().insertTeacher(teacherEntity)))
    }.catch {
        emit(Resource.failed(it.message!!))
    }

    suspend fun updateTeacher(teacherEntity: TeacherEntity): Flow<Resource<Int>> = flow {
        emit(Resource.loading())
        emit(Resource.success(appDatabase.getTeacherDao().updateTeacher(teacherEntity)))
    }.catch {
        emit(Resource.failed(it.message!!))
    }

    suspend fun getAllTeachers(): Flow<Resource<List<TeacherEntity>>> = flow {
        emit(Resource.loading())
        emit(Resource.success(appDatabase.getTeacherDao().getAllTeachers()))
    }.catch {
        emit(Resource.failed(it.message!!))
    }

}