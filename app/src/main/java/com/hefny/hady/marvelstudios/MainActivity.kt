package com.hefny.hady.marvelstudios

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val TAG = "AppDebug"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelFactory = MainViewModelFactory(ServiceLocator.getMarvelApi())
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.charactersLiveData.observe(this, { dataResource ->
            // handle loading
            showProgress(dataResource.loading)
            // handle success
            dataResource.data?.peekContent()?.results?.let { charactersList ->
                Log.d(TAG, "onCreate: $charactersList")
            }
            // handle error
            dataResource.error?.getContentIfNotHandled()?.let { errorMessage ->
                Log.d(TAG, "onCreate: error: $errorMessage")
            }
        })
    }

    private fun showProgress(isLoading: Boolean) {
        // TODO("show and hide progress dialog")
        Log.d(TAG, "showProgress: $isLoading")
    }
}