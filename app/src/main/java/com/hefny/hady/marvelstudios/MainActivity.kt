package com.hefny.hady.marvelstudios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hefny.hady.marvelstudios.utils.Resource

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val TAG = "AppDebug"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelFactory = MainViewModelFactory(ServiceLocator.getMarvelApi())
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        Log.d(TAG, "onCreate: viewmodel instance: ${viewModel.hashCode()}")
        viewModel.charactersLiveData.observe(this, { dataResource ->
            when (dataResource) {
                is Resource.Loading -> {
                    Log.d(TAG, "onCreate: loading")
                }
                is Resource.Success -> {
                    Log.d(TAG, "onCreate: success: ${dataResource.data}")
                }
                is Resource.Error -> {
                    Log.d(TAG, "onCreate: error: ${dataResource.message}")
                }
            }
        })
    }
}