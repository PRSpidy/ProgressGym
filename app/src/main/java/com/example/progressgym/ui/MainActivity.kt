package com.example.progressgym.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.progressgym.MyApp
import com.example.progressgym.R
import com.example.progressgym.ui.header.HeaderFragment
import com.example.progressgym.ui.trainingPlan.TrainingPlanFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.header_container, HeaderFragment())
            .commit()

        MyApp.userPreferences.saveUserName("Aimar")

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, TrainingPlanFragment())
        fragmentTransaction.commit()
        return
    }
}