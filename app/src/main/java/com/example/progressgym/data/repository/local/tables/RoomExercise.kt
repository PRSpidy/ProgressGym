package com.example.progressgym.data.repository.local.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "exercise",
    foreignKeys = [
        ForeignKey(
            entity = RoomMuscle::class,
            parentColumns = ["id"],
            childColumns = ["muscle_id"],
            onDelete = ForeignKey.CASCADE
        )])
data class RoomExercise (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name ="created_at") val createdAt: Date?,
    @ColumnInfo(name ="muscle_id") val muscleId: Int,
)
