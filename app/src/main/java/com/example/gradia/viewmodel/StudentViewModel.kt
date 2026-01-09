package com.example.gradia.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradia.data.Student
import com.example.gradia.data.SemesterResult
import com.example.gradia.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _student = MutableStateFlow<Student?>(repository.getStudent())
    val student: StateFlow<Student?> = _student.asStateFlow()

    private val _semesterResult = MutableStateFlow<SemesterResult?>(repository.getSemesterResult())
    val semesterResult: StateFlow<SemesterResult?> = _semesterResult.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _navigateEvent = MutableSharedFlow<Unit>()
    val navigateEvent = _navigateEvent

    private val _selectedSemesterIndex = MutableStateFlow(-1)
    val selectedSemesterIndex = _selectedSemesterIndex

    fun selectSemester(index: Int) {
        _selectedSemesterIndex.value = index
    }

    fun fetchStudentByRollNo(rollNo: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            _semesterResult.value = null

            repository.getStudentByRollNo(rollNo)
                .onSuccess { student ->
                    repository.setStudent(student)
                    _student.value = student

                    _navigateEvent.emit(Unit)

                    Log.d("StudentViewModel", "Student fetched: $student")
                }
                .onFailure { throwable ->
                    _error.value = throwable.message
                    Log.e("StudentViewModel", "Error fetching student", throwable)
                }

            _loading.value = false
        }
    }

    fun fetchStudentByRollNoAndSem(rollNo: String, sem: Int) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            _student.value = null

            repository.getStudentByRollNoAndSem(rollNo, sem)
                .onSuccess { semester ->
                    repository.setSemesterResult(semester)
                    _semesterResult.value = semester
                    Log.d("StudentViewModel", "Semester fetched: $semester")
                }
                .onFailure { throwable ->
                    _error.value = throwable.message
                    Log.e("StudentViewModel", "Error fetching semester", throwable)
                }

            _loading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}
