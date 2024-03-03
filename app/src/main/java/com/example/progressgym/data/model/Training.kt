package com.example.progressgym.data.model

import android.os.Parcelable
import com.example.progressgym.data.repository.local.tables.DayEnum
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Training (
    val id: Int,
    val name: String,
    val dayEnum: DayEnum
): Parcelable