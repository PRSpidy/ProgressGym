package com.example.progressgym.data.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrainingPlan (
    val id: Int,
    val name: String
): Parcelable