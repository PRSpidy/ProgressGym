package com.example.progressgym.data.repository.local

import com.example.progressgym.MyApp
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.CommonTrainingPlanRepository
import com.example.progressgym.data.repository.local.dao.DaoTrainingPlan
import com.example.progressgym.data.repository.local.tables.RoomTrainingPlan
import com.example.progressgym.utils.Resource
import java.util.Date

class RoomTrainingPlanDataSource: CommonTrainingPlanRepository {
    private val daoTrainingPlan: DaoTrainingPlan = MyApp.db.daoTrainingPlan()
    override suspend fun getTrainingPlans(): Resource<List<TrainingPlan>> {
        try {
            val trainingPlans = daoTrainingPlan.getAllTrainingPlan()
            val listTrainingPlan = mutableListOf<TrainingPlan>()

            trainingPlans.forEach {
                listTrainingPlan.add(
                    TrainingPlan(it.id, it.name)
                )
            }

            return Resource.success(listTrainingPlan)
        }catch (e: Exception){
            return Resource.error("Error getting training plans: ${e.message}")
        }
    }

    override suspend fun insertTrainingPlan(newTrainingPlanName: String): Resource<Int> {
        try {
            val roomTrainingPlan = RoomTrainingPlan(
                0,
                newTrainingPlanName,
                Date()
            )

            val insertResult = daoTrainingPlan.insertTrainingPlan(roomTrainingPlan)
            return Resource.success(insertResult.toInt())
        }catch (e: Exception){
            return Resource.error("Error creating training list: ${e.message}")
        }
    }
}