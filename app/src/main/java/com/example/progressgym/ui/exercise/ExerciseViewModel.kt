package com.example.progressgym.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.MuscleSpinner
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.CommonExerciseRepository
import com.example.progressgym.data.repository.CommonTrainingRepository
import com.example.progressgym.ui.training.TrainingViewModel
import com.example.progressgym.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseViewModelFactory (
    private val exerciseRepository: CommonExerciseRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return ExerciseViewModel(exerciseRepository) as T
    }
}

class ExerciseViewModel (
    private val exerciseRepository: CommonExerciseRepository
) : ViewModel() {

    private val _items = MutableLiveData<Resource<List<Exercise>>>()
    val items: LiveData<Resource<List<Exercise>>> get() = _items

    private val _exercisesByMuscle = MutableLiveData<Resource<List<MuscleSpinner>>>()
    val exercisesByMuscle: LiveData<Resource<List<MuscleSpinner>>> get() = _exercisesByMuscle

    fun getAllExercisesFromTraining(trainingId: Int){
        viewModelScope.launch {
            val roomResponse = getAllExercisesFromRoom(trainingId)
            _items.value = roomResponse
        }
    }

    fun getAllExercisesOfEachMuscle() {
        viewModelScope.launch {
            val roomResponse = getAllExercisesOfEachMuscleFromRoom()
            _exercisesByMuscle.value = roomResponse
        }
    }

    private suspend fun getAllExercisesOfEachMuscleFromRoom(): Resource<List<MuscleSpinner>> {
        return withContext(Dispatchers.IO){
            exerciseRepository.getAllExercisesOfEachMuscle()
        }
    }


    private suspend fun getAllExercisesFromRoom(trainingId: Int): Resource<List<Exercise>> {
        return withContext(Dispatchers.IO){
            exerciseRepository.getExercisesFromTraining(trainingId)
        }
    }
}