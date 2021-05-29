package com.hefny.hady.marvelstudios.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hefny.hady.marvelstudios.R

class MainActivity : AppCompatActivity(), LoadingStateListener {
    private val TAG = "AppDebug"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showLoadingState(isLoading: Boolean) {
        // TODO("show and hide progress dialog")
        Log.d(TAG, "showProgress: $isLoading")
    }
}