package com.example.gradia.data

import com.google.gson.annotations.SerializedName

data class SemesterResult(
    @SerializedName("rollNo")
    val rollNo: String,

    @SerializedName("semester")
    val sem: String,

    @SerializedName("data")
    val data: SemesterResultData
)
