package com.example.progressgym.ui.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.CommonTrainingPlanRepository
import com.example.progressgym.data.repository.CommonTrainingRepository
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingViewModelFactory(
    private val trainingRepository: CommonTrainingRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return TrainingViewModel(trainingRepository) as T
    }
}
class TrainingViewModel (
    private val trainingRepository: CommonTrainingRepository
) : ViewModel() {

    private val _items = MutableLiveData<Resource<List<Training>>>()
    val items: LiveData<Resource<List<Training>>> get() = _items

    private val _created = MutableLiveData<Resource<Int>>()
    val created: LiveData<Resource<Int>> get() = _created

    private val _freeDays = MutableLiveData<Resource<List<DayEnum>>>()
    val freeDays: LiveData<Resource<List<DayEnum>>> get() = _freeDays

    fun getTrainings(trainingPlanId: Int) {
        viewModelScope.launch {
            val roomResponse = getTrainingFromRoom(trainingPlanId)
            _items.value = roomResponse
        }
    }

    fun addNewTraining(newTrainingName: Training, trainingPlanId: Int) {
        viewModelScope.launch {
            val roomResponse = createNewTraining(newTrainingName, trainingPlanId)
            _created.value = roomResponse
        }
    }

    fun getFreeDays(id: Int) {
        viewModelScope.launch {
            val roomResponse = getFreeDaysRoom(id)
            _freeDays.value = roomResponse
        }
    }

    private suspend fun getFreeDaysRoom(id: Int): Resource<List<DayEnum>> {
        return withContext(Dispatchers.IO){
            trainingRepository.getFreeDays(id)
        }
    }

    private suspend fun createNewTraining(newTrainingName: Training, trainingPlanId: Int): Resource<Int> {
        return withContext(Dispatchers.IO){
            trainingRepository.insertTrainingPlan(newTrainingName, trainingPlanId)
        }
    }

    private suspend fun getTrainingFromRoom(trainingPlanId: Int): Resource<List<Training>> {
        return withContext(Dispatchers.IO){
            trainingRepository.getTrainings(trainingPlanId)
        }
    }

}