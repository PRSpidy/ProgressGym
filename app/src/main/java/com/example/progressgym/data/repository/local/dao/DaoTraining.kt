package com.example.progressgym.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.data.repository.local.tables.RoomTraining
import com.example.progressgym.data.repository.local.tables.RoomTrainingPlan

@Dao
interface DaoTraining {

    @Query("SELECT * FROM training WHERE training.training_plan_id = :trainingPlanId ORDER BY CASE training.day " +
            "WHEN 'MONDAY' THEN 1 " +
            "WHEN 'TUESDAY' THEN 2 " +
            "WHEN 'WEDNESDAY' THEN 3 " +
            "WHEN 'THURSDAY' THEN 4 " +
            "WHEN 'FRIDAY' THEN 5 " +
            "WHEN 'SATURDAY' THEN 6 " +
            "WHEN 'SUNDAY' THEN 7 " +
            "END ASC")
    suspend fun getAllTrainingByTrainingPlanId(trainingPlanId: Int): List<RoomTraining>
    @Query("SELECT * FROM training WHERE training.training_plan_id = :trainingPlanId AND training.day = :dayEnum")
    suspend fun getTrainingByTrainingPlanIdAndDay(trainingPlanId: Int, dayEnum: DayEnum): List<RoomTraining>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTraining(training: RoomTraining): Long

}