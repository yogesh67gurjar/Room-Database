package com.yogesh.room.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.room.data.models.TeacherEntity
import com.yogesh.room.data.repository.SchoolRepository
import com.yogesh.room.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(private val studentRepository: SchoolRepository) :
    ViewModel() {

    private val _getAllTeachersStateFlow: MutableStateFlow<Resource<List<TeacherEntity>>?> =
        MutableStateFlow(null)
    val getAllTeachersStateFlow: StateFlow<Resource<List<TeacherEntity>>?> =
        _getAllTeachersStateFlow

    private val _insertTeacherStateFlow: MutableStateFlow<Resource<Long>?> =
        MutableStateFlow(null)
    val insertTeacherStateFlow: StateFlow<Resource<Long>?> =
        _insertTeacherStateFlow

    private val _updateTeacherStateFlow: MutableStateFlow<Resource<Int>?> =
        MutableStateFlow(null)
    val updateTeacherStateFlow: StateFlow<Resource<Int>?> =
        _updateTeacherStateFlow

    fun addOrUpdateTeacher(teacherEntity: TeacherEntity) {
        viewModelScope.launch {
            if (teacherEntity.id == 0) {
                studentRepository.insertTeacher(teacherEntity).collect {
                    _insertTeacherStateFlow.value = it
                }
            } else {
                studentRepository.updateTeacher(teacherEntity).collect {
                    _updateTeacherStateFlow.value = it
                }
            }
        }
    }

    fun getAllTeachers() {
        viewModelScope.launch {
            studentRepository.getAllTeachers().collect {
                _getAllTeachersStateFlow.value = it
            }
        }
    }


}