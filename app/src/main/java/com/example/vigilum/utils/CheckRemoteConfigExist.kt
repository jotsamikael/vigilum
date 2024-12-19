package com.example.vigilum.utils

import android.util.Log
import com.example.vigilum.data.repository.RemoteConfigurationRepository
import kotlinx.coroutines.runBlocking

fun checkRemoteConfigExist(repository: RemoteConfigurationRepository):Boolean{
    var configExists = false
    runBlocking {
        val remoteConfig = repository.getRemoteConfigFromDB()
        Log.i("ISREMOTECONFIG", "device serial number is: ${remoteConfig}")
        if(remoteConfig!=null){
            configExists = true
        }
    }
    return configExists
}