package com.example.progressgym.data.repository.local

import android.util.Log
import com.example.progressgym.MyApp
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.CommonTrainingPlanRepository
import com.example.progressgym.data.repository.CommonTrainingRepository
import com.example.progressgym.data.repository.local.dao.DaoTraining
import com.example.progressgym.data.repository.local.dao.DaoTrainingPlan
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.data.repository.local.tables.RoomTraining
import com.example.progressgym.data.repository.local.tables.RoomTrainingPlan
import com.example.progressgym.utils.Resource
import java.util.Date

class RoomTrainingDataSource: CommonTrainingRepository {
    private val daoTraining: DaoTraining = MyApp.db.daoTraining()
    override suspend fun getTrainings(trainingPlanId: Int): Resource<List<Training>> {
        try {
            val training = daoTraining.getAllTrainingByTrainingPlanId(trainingPlanId)
            val listTraining = mutableListOf<Training>()

            training.forEach {
                listTraining.add(
                    Training(it.id, it.name, it.day)
                )
            }
            return Resource.success(listTraining)
        }catch (e: Exception){
            return Resource.error("Error getting trainings: ${e.message}")
        }
    }

    override suspend fun insertTrainingPlan(
        newTraining: Training,
        trainingPlanId: Int
    ): Resource<Int> {
        try {
            if(daoTraining.getTrainingByTrainingPlanIdAndDay(trainingPlanId, newTraining.dayEnum).isNotEmpty()){
                return Resource.error("Day of the training exist")
            }
            val roomTraining = RoomTraining(
                0,
                newTraining.name,
                newTraining.dayEnum,
                Date(),
                trainingPlanId
            )

            val insertResult = daoTraining.insertTraining(roomTraining)
            return Resource.success(insertResult.toInt())
        }catch (e: Exception){
            return Resource.error("Error creating training list: ${e.message}")
        }
    }

    override suspend fun getFreeDays(trainingPlanId: Int): Resource<List<DayEnum>> {

        try {
            val training = daoTraining.getAllTrainingByTrainingPlanId(trainingPlanId)
            val listDays = mutableListOf<DayEnum>()
            training.forEach{
                listDays.add(it.day)
            }
            return Resource.success(listDays)
        }catch (e: Exception){
            return Resource.error("Error getting free days list: ${e.message}")
        }

    }


}