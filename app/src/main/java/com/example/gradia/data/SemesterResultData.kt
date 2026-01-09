package com.example.gradia.data

import com.google.gson.annotations.SerializedName

data class SemesterResultData(
    @SerializedName("subject")
    val subjects: List<Subject>,

    @SerializedName("sgpa")
    val sgpa: Float,

    @SerializedName("status")
    val status: Boolean
)
