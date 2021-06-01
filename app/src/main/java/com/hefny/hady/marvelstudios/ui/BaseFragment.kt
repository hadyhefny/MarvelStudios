package com.hefny.hady.marvelstudios.ui

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hefny.hady.marvelstudios.MainViewModel
import com.hefny.hady.marvelstudios.MainViewModelFactory
import com.hefny.hady.marvelstudios.ServiceLocator

abstract class BaseFragment : Fragment() {
    private val TAG = "AppDebug"
    lateinit var uiCommunicationListener: UICommunicationListener
    private val viewModelFactory = MainViewModelFactory(ServiceLocator.getMarvelApi())
    val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "onAttach: $context must implement LoadingStateListener")
        }
    }
}