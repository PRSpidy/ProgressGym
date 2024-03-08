package com.example.progressgym.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.progressgym.MyApp
import com.example.progressgym.R
import com.example.progressgym.data.repository.local.RoomExerciseDataSource
import com.example.progressgym.ui.initial.InitialFragment
import kotlinx.coroutines.launch

class AuthActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(MyApp.firstTimeRunningApp){
            lifecycleScope.launch {
                insertExercises()
            }

        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, InitialFragment())
        fragmentTransaction.commit()
        return
    }

    suspend fun insertExercises(){
        val roomExercise= RoomExerciseDataSource()
        if(roomExercise.addAllExercises()){
            MyApp.updateFirstTimeRunning(false)
        }
    }
}