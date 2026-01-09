package com.example.gradia.repository

import com.example.gradia.ApiService
import com.example.gradia.data.Student
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepository @Inject constructor(
    private val apiService: ApiService
) {

    private var cachedStudent: Student? = null

    fun setStudent(student: Student?) {
        cachedStudent = student
    }

    fun getStudent(): Student? = cachedStudent

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

}
