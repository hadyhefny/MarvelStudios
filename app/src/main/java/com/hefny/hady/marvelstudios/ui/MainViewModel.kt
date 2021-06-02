package com.hefny.hady.marvelstudios.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.hefny.hady.marvelstudios.api.MarvelApi
import com.hefny.hady.marvelstudios.api.responses.DataContainerResponse
import com.hefny.hady.marvelstudios.api.responses.MainResponse
import com.hefny.hady.marvelstudios.models.Character
import com.hefny.hady.marvelstudios.models.MarvelSummary
import com.hefny.hady.marvelstudios.ui.charactersList.datasource.CharactersDataSource
import com.hefny.hady.marvelstudios.utils.ErrorUtils
import com.hefny.hady.marvelstudios.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val marvelApi: MarvelApi
) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val _marvelSummariesMutableLiveData =
        MutableLiveData<Resource<ArrayList<MarvelSummary>>>()
    val marvelSummariesLiveData: LiveData<Resource<ArrayList<MarvelSummary>>>
        get() = _marvelSummariesMutableLiveData

    private var currentQueryValue: String? = null
    private var currentSearchResult: LiveData<PagingData<Character>>? = null

    fun getPagingCharacters(name: String? = null): LiveData<PagingData<Character>> {
        val lastResult = currentSearchResult
        if (name == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = name
        val newResult = Pager(
            PagingConfig(20)
        ) {
            CharactersDataSource(marvelApi, name)
        }.liveData.cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun getMarvelSummaries(url: String, type: String) {
        _marvelSummariesMutableLiveData.value = Resource.loading(true)
        marvelApi.getMarvelSummaries(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                SingleObserver<MainResponse<DataContainerResponse<MarvelSummary>>> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onSuccess(t: MainResponse<DataContainerResponse<MarvelSummary>>?) {
                    t?.data?.results?.let {
                        _marvelSummariesMutableLiveData.value =
                            Resource.data(data = it, type = type)
                    }
                }

                override fun onError(e: Throwable?) {
                    _marvelSummariesMutableLiveData.value =
                        Resource.error(ErrorUtils.parseError(e).message)
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}