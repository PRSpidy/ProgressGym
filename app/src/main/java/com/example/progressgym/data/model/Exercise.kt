package com.example.progressgym.data.model

import android.os.Parcelable
import com.example.progressgym.data.repository.local.tables.DayEnum
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Exercise (
    val id: Int,
    val name: String,
    val muscle: Muscle
): Parcelable