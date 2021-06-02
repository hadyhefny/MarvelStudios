package com.hefny.hady.marvelstudios.ui

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    private val TAG = "AppDebug"
    lateinit var uiCommunicationListener: UICommunicationListener
    val viewModel: MainViewModel by activityViewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "onAttach: $context must implement LoadingStateListener")
        }
    }
}