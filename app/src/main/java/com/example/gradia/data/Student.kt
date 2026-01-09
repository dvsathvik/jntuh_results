
package com.example.gradia.data

import com.google.gson.annotations.SerializedName

data class Student(

    @SerializedName("rollNo")
    val rollNo : String,

    @SerializedName("fullName")
    val name: String,

    @SerializedName("backlogs")
    val currentBacklogs: List<String>,

    @SerializedName("results")
    val results: Results
)