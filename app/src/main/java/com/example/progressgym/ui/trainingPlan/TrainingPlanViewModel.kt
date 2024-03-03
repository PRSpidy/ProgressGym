package com.example.progressgym.ui.trainingPlan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.CommonTrainingPlanRepository
import com.example.progressgym.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingPlanViewModelFactory(
    private val trainingPlanRepository: CommonTrainingPlanRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return TrainingPlanViewModel(trainingPlanRepository) as T
    }
}
class TrainingPlanViewModel (
    private val trainingPlanRepository: CommonTrainingPlanRepository
) : ViewModel() {

    private val _items = MutableLiveData<Resource<List<TrainingPlan>>>()
    val items: LiveData<Resource<List<TrainingPlan>>> get() = _items

    private val _created = MutableLiveData<Resource<Int>>()
    val created: LiveData<Resource<Int>> get() = _created

    init {
        getTrainingPlans()
    }

    fun getTrainingPlans() {
        viewModelScope.launch {
            val roomResponse = getTrainingPlansFromRoom()
            _items.value = roomResponse
        }
    }

    fun addNewTrainingPlan(newTrainingPlanName: String) {
        viewModelScope.launch {
            val roomResponse = createNewTrainingPlan(newTrainingPlanName)
            _created.value = roomResponse
        }
    }

    private suspend fun createNewTrainingPlan(newTrainingPlanName: String): Resource<Int> {
        return withContext(Dispatchers.IO){
            trainingPlanRepository.insertTrainingPlan(newTrainingPlanName)
        }
    }

    private suspend fun getTrainingPlansFromRoom(): Resource<List<TrainingPlan>> {
        return withContext(Dispatchers.IO){
            trainingPlanRepository.getTrainingPlans()
        }
    }


}