package com.example.progressgym.data.repository

import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.utils.Resource

interface CommonTrainingPlanRepository {
    suspend fun getTrainingPlans(): Resource<List<TrainingPlan>>

    suspend fun insertTrainingPlan(newTrainingPlanName: String): Resource<Int>

}