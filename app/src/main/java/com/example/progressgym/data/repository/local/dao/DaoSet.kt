package com.example.progressgym.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.progressgym.data.model.Set
import com.example.progressgym.data.repository.local.tables.RoomSet
import com.example.progressgym.data.repository.local.tables.RoomTrainingExerciseSet
import java.util.Date

@Dao
interface DaoSet {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSet(set: RoomSet): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun relationSetTrainingExercise(trainingExerciseSet: RoomTrainingExerciseSet): Long


    @Query("Update 'set' set reps = :reps, weight = :weight, reps_obj = :repsObj, weight_obj = :weightObj where id = :id")
    suspend fun updateSet(id: Int, repsObj: Int, weightObj: Float, reps: Int, weight: Float): Int

    @Query("SELECT set1.* from 'set' as set1 join training_exercise_set on set1.id = training_exercise_set.set_id where training_exercise_set.date = :date and training_exercise_set.training_id = :trainingId and training_exercise_set.exericise_id = :exerciseId")
    suspend fun getSet(date: Date, trainingId: Int, exerciseId: Int): List<RoomSet>

    @Query("SELECT DISTINCT training_exercise_set.date FROM  'set' as set1 join training_exercise_set on set1.id = training_exercise_set.set_id WHERE training_exercise_set.training_id = :trainingId and training_exercise_set.exericise_id = :exerciseId ORDER BY training_exercise_set.date DESC")
    suspend fun getDays(trainingId: Int, exerciseId: Int): List<Date>
}