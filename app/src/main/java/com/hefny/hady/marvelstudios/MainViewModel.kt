package com.hefny.hady.marvelstudios

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.hefny.hady.marvelstudios.api.MarvelApi
import com.hefny.hady.marvelstudios.models.Character
import com.hefny.hady.marvelstudios.ui.charactersList.datasource.CharactersDataSource

class MainViewModel(private val marvelApi: MarvelApi) : ViewModel() {
    fun getPagingCharacters(name: String? = null): LiveData<PagingData<Character>> {
        return Pager(
            PagingConfig(20)
        ) {
            CharactersDataSource(marvelApi, name)
        }.liveData
    }
}