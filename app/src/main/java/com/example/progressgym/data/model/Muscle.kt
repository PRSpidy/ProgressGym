package com.example.progressgym.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Muscle (
    val id: Int,
    val name: String
): Parcelable