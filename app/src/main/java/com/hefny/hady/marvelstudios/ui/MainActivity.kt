package com.hefny.hady.marvelstudios.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.hefny.hady.marvelstudios.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UICommunicationListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, SplashFragment())
                .commit()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            hideKeyboard()
            showProgressBar(false)
        }
    }

    override fun showProgressBar(isLoading: Boolean) {
        progressDialog.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun showError(errorMessage: String) {
        Snackbar.make(main_coordinatorlayout, errorMessage, Snackbar.LENGTH_SHORT).show()
    }

    override fun hideKeyboard() {
        main_coordinatorlayout.requestFocus()
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(main_coordinatorlayout.rootView.windowToken, 0)
    }
}