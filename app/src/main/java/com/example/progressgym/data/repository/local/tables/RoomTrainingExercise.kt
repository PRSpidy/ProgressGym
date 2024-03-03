package com.example.progressgym.data.repository.local.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.Date


@Entity(tableName = "training_exercise",
    foreignKeys = [
        ForeignKey(
            entity = RoomTraining::class,
            parentColumns = ["id"],
            childColumns = ["training_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoomExercise::class,
            parentColumns = ["id"],
            childColumns = ["exericise_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["training_id", "exericise_id"]
)
data class RoomTrainingExercise (
    @ColumnInfo(name ="training_id") val userId: Int,
    @ColumnInfo(name ="exericise_id") val chatId: Int,
    @ColumnInfo(name ="created_at") val createdAt: Date?,
)