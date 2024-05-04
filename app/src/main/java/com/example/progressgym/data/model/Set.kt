package com.example.progressgym.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Set (
    var id: Int,
    val setNumber: Int,
    val repsObj: Int,
    var weightObj: Float,
    var reps: Int,
    var weight: Float,

    ): Parcelable