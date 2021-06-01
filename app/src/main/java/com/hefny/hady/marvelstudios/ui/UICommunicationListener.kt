package com.hefny.hady.marvelstudios.ui

interface UICommunicationListener {
    fun showProgressBar(isLoading: Boolean)
    fun showError(errorMessage: String)
    fun hideKeyboard()
}