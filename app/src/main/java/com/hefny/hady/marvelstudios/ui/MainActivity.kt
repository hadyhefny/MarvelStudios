package com.hefny.hady.marvelstudios.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.ui.charactersList.CharactersListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoadingStateListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, CharactersListFragment())
            .commit()
    }

    override fun showLoadingState(isLoading: Boolean) {
        progressDialog.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}