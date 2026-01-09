
package com.example.gradia.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Semester(
    @SerializedName("semester")
    val sem: String,

    @SerializedName("subject")
    val subject: List<Subject>,

    @SerializedName("sgpa")
    val sgpa: Double? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("credits")
    var credits : Int? = null
): Parcelable
