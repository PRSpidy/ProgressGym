package com.example.progressgym.data.repository.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.progressgym.data.repository.local.converters.Converters
import com.example.progressgym.data.repository.local.dao.DaoExercise
import com.example.progressgym.data.repository.local.dao.DaoMuscle
import com.example.progressgym.data.repository.local.dao.DaoSet
import com.example.progressgym.data.repository.local.dao.DaoTraining
import com.example.progressgym.data.repository.local.dao.DaoTrainingPlan
import com.example.progressgym.data.repository.local.tables.RoomExercise
import com.example.progressgym.data.repository.local.tables.RoomMuscle
import com.example.progressgym.data.repository.local.tables.RoomSet
import com.example.progressgym.data.repository.local.tables.RoomTraining
import com.example.progressgym.data.repository.local.tables.RoomTrainingExercise
import com.example.progressgym.data.repository.local.tables.RoomTrainingExerciseSet
import com.example.progressgym.data.repository.local.tables.RoomTrainingPlan

@Database(
    entities = [RoomExercise::class, RoomMuscle::class, RoomSet::class, RoomTraining::class, RoomTrainingExercise::class, RoomTrainingExerciseSet::class, RoomTrainingPlan::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun daoExercise(): DaoExercise
    abstract fun daoMuscle(): DaoMuscle
    abstract fun daoSet(): DaoSet
    abstract fun daoTraining(): DaoTraining
    abstract fun daoTrainingPlan(): DaoTrainingPlan
}
