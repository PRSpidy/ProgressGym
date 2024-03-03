package com.example.progressgym.data.repository

import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.utils.Resource

interface CommonTrainingRepository {
    suspend fun getTrainings(trainingPlanId: Int): Resource<List<Training>>

    suspend fun insertTrainingPlan(newTraining: Training, trainingPlanId: Int): Resource<Int>

    suspend fun getFreeDays(trainingPlanId: Int): Resource<List<DayEnum>>

}