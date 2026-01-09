
package com.example.gradia.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Semester(
    @SerializedName("subject")
    val subject: List<Subject>,

    @SerializedName("sgpa")
    val sgpa: Double? = null,

    @SerializedName("status")
    val status: Boolean? = null,

    var credits : Int? = null
): Parcelable
