package com.example.progressgym.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.progressgym.data.repository.local.tables.RoomExercise
import com.example.progressgym.data.repository.local.tables.RoomMuscle

@Dao
interface DaoMuscle {

    @Query("SELECT * FROM muscle WHERE muscle.id = :muscleId")
    suspend fun getMuscleById(muscleId: Int): List<RoomMuscle>

    @Query("SELECT * FROM muscle")
    suspend fun getAllMuscle(): List<RoomMuscle>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMuscle(role: RoomMuscle): Long


}