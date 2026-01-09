package com.example.gradia.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Subject(
    @SerializedName("code")
    val code: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("credit")
    val credit: Float,

    @SerializedName("grade")
    val grade: String,

    @SerializedName("gp")
    val gp: Int
) : Parcelable

