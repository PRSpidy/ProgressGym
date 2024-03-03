package com.example.progressgym.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.progressgym.R
import com.example.progressgym.ui.initial.InitialFragment

class AuthActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, InitialFragment())
        fragmentTransaction.commit()
        return
    }
}