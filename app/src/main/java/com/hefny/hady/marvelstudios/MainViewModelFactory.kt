package com.hefny.hady.marvelstudios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hefny.hady.marvelstudios.api.MarvelApi

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val marvelApi: MarvelApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(marvelApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}