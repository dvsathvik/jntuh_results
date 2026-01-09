
package com.example.gradia.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Semester(
    @SerializedName("semester")
    val sem: String,

    @SerializedName("subjects")
    val subject: List<Subject> = emptyList(),

    @SerializedName("sgpa")
    val sgpa: Double? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("credits")
    var credits : Double? = null
): Parcelable
