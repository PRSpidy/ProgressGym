package com.example.progressgym.ui.set

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.progressgym.data.repository.CommonExerciseRepository
import com.example.progressgym.data.repository.CommonSetRepository
import com.example.progressgym.ui.exercise.ExerciseViewModel

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



}