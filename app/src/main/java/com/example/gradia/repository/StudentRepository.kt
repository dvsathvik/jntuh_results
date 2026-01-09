package com.example.gradia.repository

import com.example.gradia.ApiService
import com.example.gradia.data.SemesterResult
import com.example.gradia.data.Student
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepository @Inject constructor(
    private val apiService: ApiService
) {

    private var cachedStudent: Student? = null
    private var cachedSemester: SemesterResult? = null

    fun setStudent(student: Student?) {
        cachedStudent = student
    }

    fun getStudent(): Student? = cachedStudent

    fun setSemesterResult(result: SemesterResult?) {
        cachedSemester = result
    }

    fun getSemesterResult(): SemesterResult? = cachedSemester


    suspend fun getStudentByRollNo(rollNo: String): Result<Student> {
        return try {
            val response = apiService.getSpecificStudent(rollNo)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getStudentByRollNoAndSem(rollNo: String, sem: Int): Result<SemesterResult> {
        return try {
            val response = apiService.getSpecificStudentSem(rollNo, sem)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
