package com.example.progressgym.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.progressgym.data.repository.local.tables.RoomTrainingPlan

@Dao
interface DaoTrainingPlan {

    @Query("SELECT * FROM training_plan ORDER BY training_plan.id DESC")
    suspend fun getAllTrainingPlan(): List<RoomTrainingPlan>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrainingPlan(trainingPlan: RoomTrainingPlan): Long
}