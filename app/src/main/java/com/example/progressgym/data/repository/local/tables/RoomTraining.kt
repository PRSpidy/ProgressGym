package com.example.progressgym.data.repository.local.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "training",
    foreignKeys = [
        ForeignKey(
            entity = RoomTrainingPlan::class,
            parentColumns = ["id"],
            childColumns = ["training_plan_id"],
            onDelete = ForeignKey.CASCADE
        )])
data class RoomTraining (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name = "day") val day: DayEnum,
    @ColumnInfo(name ="created_at") val createdAt: Date?,
    @ColumnInfo(name ="training_plan_id") val trainigPlanId: Int,
    )