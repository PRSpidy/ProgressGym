package com.example.progressgym.data.repository.local.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "training_plan")
data class RoomTrainingPlan (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name ="created_at") val createdAt: Date?,
)