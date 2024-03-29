package com.example.progressgym

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.room.Room
import com.example.progressgym.data.repository.local.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlin.properties.Delegates

@HiltAndroidApp
class MyApp : Application() {

    companion object {
        lateinit var context: Context
        lateinit var userPreferences: UserPreferences
        var wifiStatus by Delegates.notNull<Boolean>()

        lateinit var db: AppDatabase

        private const val PREFS_NAME = "MyAppPrefs"
        private const val FIRST_TIME_RUNNING_KEY = "firstTimeRunning"

        var firstTimeRunningApp: Boolean = true
            private set(value) {
                field = value
            }

        fun updateFirstTimeRunning(value: Boolean) {
            firstTimeRunningApp = value
            val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().putBoolean(FIRST_TIME_RUNNING_KEY, value).apply()
        }

    }

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    override fun onCreate() {
        super.onCreate()
        context = this;
        userPreferences = UserPreferences()
        db = Room
            .databaseBuilder(this,AppDatabase::class.java,getString(R.string.room_database_name))
            .build()
        // Required initialization logic here!

        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        firstTimeRunningApp = prefs.getBoolean(FIRST_TIME_RUNNING_KEY, true)


        if (firstTimeRunningApp) {
            firstTimeRunningApp = true
            prefs.edit().putBoolean(FIRST_TIME_RUNNING_KEY, false).apply()
        }

    }




    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    override fun onConfigurationChanged ( newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    override fun onLowMemory() {
        super.onLowMemory()
    }
    override fun onTerminate() {
        super.onTerminate()
    }

}