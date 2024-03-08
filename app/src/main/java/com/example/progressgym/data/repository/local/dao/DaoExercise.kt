package com.example.progressgym.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.data.repository.local.tables.RoomExercise
import com.example.progressgym.data.repository.local.tables.RoomMuscle
import com.example.progressgym.data.repository.local.tables.RoomTraining

@Dao
interface DaoExercise {
    @Query("SELECT * FROM exercise join training_exercise on exercise.id = training_exercise.exericise_id WHERE training_exercise.training_id = :trainingId")
    suspend fun getAllExercisesByTrainingPlanId(trainingId: Int): List<RoomExercise>

    @Query("SELECT * FROM exercise WHERE exercise.muscle_id = :muscleId")
    suspend fun getAllExercisesByMuscleId(muscleId: Int): List<RoomExercise>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercise(role: RoomExercise): Long
}