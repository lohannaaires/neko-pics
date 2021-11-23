package com.lohanna.nekopics.application

import android.app.Application
import android.content.Context
import com.lohanna.nekopics.application.NekoPicsApp.Companion.applicationContext
import com.lohanna.nekopics.utils.checkForInternet
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NekoPicsApp: Application(){

    init {
        instance = this
    }

    companion object{
        private var instance: NekoPicsApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        fun getInternetStatus(): Boolean{
           return checkConnection()
        }

    }

}

fun checkConnection(): Boolean {
    if (checkForInternet(applicationContext())) {
        //Toast.makeText(applicationContext(), "Connected", Toast.LENGTH_SHORT).show()
        return true
    }
    //Toast.makeText(applicationContext(), "Disconnected", Toast.LENGTH_SHORT).show()
    return false
}