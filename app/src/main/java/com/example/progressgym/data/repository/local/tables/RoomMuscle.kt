package com.example.progressgym.data.repository.local.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "muscle")
data class RoomMuscle (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name ="created_at") val createdAt: Date?,
)