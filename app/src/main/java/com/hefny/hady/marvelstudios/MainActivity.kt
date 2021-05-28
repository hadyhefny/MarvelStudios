package com.hefny.hady.marvelstudios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val TAG = "AppDebug"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        viewModel.charactersLiveData.observe(this, { dataResource ->
            Log.d(TAG, "onCreate: ${dataResource.data}")
            Log.d(TAG, "onCreate: message: ${dataResource.message}")
        })
    }
}