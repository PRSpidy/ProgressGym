package com.example.progressgym.data.repository.local.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "set")
data class RoomSet (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name ="set_number") val setNumber: Int,
    @ColumnInfo(name ="reps") val reps: Int,
    @ColumnInfo(name ="weight") val weight: Int,
    @ColumnInfo(name ="reps_obj") val repsObj: Int,
    @ColumnInfo(name ="weight_obj") val weightObj: Int,
    @ColumnInfo(name ="created_at") val createdAt: Date?,
    )