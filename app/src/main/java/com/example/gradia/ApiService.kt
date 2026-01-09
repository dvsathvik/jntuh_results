package com.example.gradia

import com.example.gradia.data.Student
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("results/{rollNo}")
    suspend fun getSpecificStudent(
        @Path("rollNo") rollNo: String
    ): Response<Student>

}
