package com.example.progressgym.data.repository

import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.MuscleSpinner
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.utils.Resource

interface CommonExerciseRepository {
    suspend fun getExercisesFromTraining(trainingId: Int): Resource<List<Exercise>>
    suspend fun getAllExercisesOfEachMuscle(): Resource<List<MuscleSpinner>>
}