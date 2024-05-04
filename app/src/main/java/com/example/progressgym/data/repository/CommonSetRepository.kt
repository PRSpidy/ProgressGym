package com.example.progressgym.data.repository

import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.Muscle
import com.example.progressgym.data.model.MuscleSpinner
import com.example.progressgym.data.model.Set
import com.example.progressgym.data.model.TablaItem
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.utils.Resource
import java.util.Date

interface CommonSetRepository {
    suspend fun insetSet(date: Date, set: Set, training: Training, exercise: Exercise): Resource<Int>
    suspend fun getSet(date: Date, trainingId: Int, exerciseId: Int): Resource<List<TablaItem>>
    suspend fun getDays(trainingId: Int, exerciseId: Int): Resource<List<Date>>

}