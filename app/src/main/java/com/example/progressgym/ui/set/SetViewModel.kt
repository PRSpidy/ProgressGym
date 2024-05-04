package com.example.progressgym.ui.set

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.Set
import com.example.progressgym.data.model.TablaItem
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.repository.CommonSetRepository
import com.example.progressgym.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class SetViewModelFactory (
    private val setRepository: CommonSetRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return SetViewModel(setRepository) as T
    }
}

class SetViewModel (
    private val setRepository: CommonSetRepository
) : ViewModel() {

    private val _created = MutableLiveData<Resource<Int>>()
    val created: LiveData<Resource<Int>> get() = _created

    private val _item = MutableLiveData<Resource<List<TablaItem>>>()
    val item: LiveData<Resource<List<TablaItem>>> get() = _item

    private val _days = MutableLiveData<Resource<List<Date>>>()
    val days: LiveData<Resource<List<Date>>> get() = _days

    fun insertSet(date: Date, set: Set, training: Training, exercise: Exercise) {
        viewModelScope.launch {
            val roomResponse = createSet(date, set, training, exercise)
            _created.value = roomResponse
        }
    }

    private suspend fun createSet(date: Date, set: Set, training: Training, exercise: Exercise): Resource<Int> {
        return withContext(Dispatchers.IO){
            setRepository.insetSet(date, set, training, exercise)
        }
    }

    fun getSet(date: Date, trainingId: Int, exerciseId: Int) {
        viewModelScope.launch {
            val roomResponse = getSetFromRoom(date, trainingId, exerciseId)
            _item.value = roomResponse
        }
    }

    fun getDaysOfSet(trainingId: Int, exerciseId: Int) {
        viewModelScope.launch {
            val roomResponse = getDaysOfSetFromRoom(trainingId, exerciseId)
            _days.value = roomResponse
        }
    }

    private suspend fun getDaysOfSetFromRoom(trainingId: Int, exerciseId: Int): Resource<List<Date>> {
        return withContext(Dispatchers.IO){
            setRepository.getDays(trainingId, exerciseId)
        }
    }

    private suspend fun getSetFromRoom(date: Date, trainingId: Int, exerciseId: Int): Resource<List<TablaItem>> {
        return withContext(Dispatchers.IO){
            setRepository.getSet(date, trainingId, exerciseId)
        }
    }




}