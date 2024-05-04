package com.example.progressgym.data.repository.local

import android.util.Log
import com.example.progressgym.MyApp
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.ExerciseSpinner
import com.example.progressgym.data.model.Muscle
import com.example.progressgym.data.model.MuscleSpinner
import com.example.progressgym.data.model.Set
import com.example.progressgym.data.model.TablaItem
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.repository.CommonExerciseRepository
import com.example.progressgym.data.repository.CommonSetRepository
import com.example.progressgym.data.repository.local.dao.DaoExercise
import com.example.progressgym.data.repository.local.dao.DaoMuscle
import com.example.progressgym.data.repository.local.dao.DaoSet
import com.example.progressgym.data.repository.local.dao.DaoTraining
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.data.repository.local.tables.RoomExercise
import com.example.progressgym.data.repository.local.tables.RoomMuscle
import com.example.progressgym.data.repository.local.tables.RoomSet
import com.example.progressgym.data.repository.local.tables.RoomTrainingExerciseSet
import com.example.progressgym.utils.Resource
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RoomSetDataSource: CommonSetRepository {
    private val daoSet: DaoSet = MyApp.db.daoSet()
    override suspend fun insetSet(date: Date, set: Set, training: Training, exercise: Exercise): Resource<Int> {
        try {
            var result: Int = 0;
            Log.i("Create", set.id.toString())
            if(set.id == 0){
                //Insert set
                val calendar = Calendar.getInstance().apply {
                    time = date
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                val date = calendar.time

                Log.i("Create", date.toString())
                val roomSet = RoomSet(set.id, set.setNumber, set.reps, set.weight, set.repsObj, set.weightObj, date)

                val setId = daoSet.insertSet(roomSet)
                set.id = setId.toInt();

                val trainingExerciseSet = RoomTrainingExerciseSet(training.id, exercise.id, set.id, date)
                daoSet.relationSetTrainingExercise(trainingExerciseSet)
                Log.i("Create", set.toString())
                Log.i("Create", trainingExerciseSet.toString())
                Log.i("Createid", setId.toString())
                result = setId.toInt()

            }else{
                //Update set
                daoSet.updateSet(set.id, set.repsObj, set.weightObj, set.reps, set.weight)

                result = 0;
                Log.i("Update", "Update")
            }


            return Resource.success(result)
        }catch (e: Exception){
            return Resource.error("Error creating set: ${e.message}")
        }
    }

    override suspend fun getSet(
        date: Date,
        trainingId: Int,
        exerciseId: Int
    ): Resource<List<TablaItem>> {
        try {
            val calendar = Calendar.getInstance().apply {
                time = date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val date1 = calendar.time

            Log.i("Create", date1.toString())
            Log.i("Create", trainingId.toString() + " " + exerciseId.toString())
            val listOfSets = daoSet.getSet(date1, trainingId, exerciseId)
            Log.i("Create", listOfSets.toString())
            val listSet = mutableListOf<TablaItem>()
            listOfSets.forEach{
                listSet.add(TablaItem(it.id, it.setNumber, it.repsObj.toString(), it.weightObj.toString(), it.reps.toString(), it.weight.toString()))
            }
            return Resource.success(listSet)
        }catch (e: Exception){
            return Resource.error("Error getting set: ${e.message}")
        }
    }

    override suspend fun getDays(trainingId: Int, exerciseId: Int): Resource<List<Date>> {
        try {

            val listOfDays= daoSet.getDays(trainingId, exerciseId).toMutableList()
            val calendar = Calendar.getInstance().apply {
                time = Date()
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val today = calendar.time

            val todayInList = listOfDays.any { it == today }

            if (!todayInList) {
                listOfDays.add(0, today)
            }


            val distinct = listOfDays.distinct().toList()
            Log.i("list of days", distinct.toString())
            return Resource.success(distinct)
        }catch (e: Exception){
            return Resource.error("Error getting days: ${e.message}")
        }
    }


}