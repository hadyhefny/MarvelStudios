package com.hefny.hady.marvelstudios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.hefny.hady.marvelstudios.api.MarvelApi
import com.hefny.hady.marvelstudios.api.responses.DataContainerResponse
import com.hefny.hady.marvelstudios.api.responses.MainResponse
import com.hefny.hady.marvelstudios.models.Character
import com.hefny.hady.marvelstudios.models.MarvelSummary
import com.hefny.hady.marvelstudios.ui.charactersList.datasource.CharactersDataSource
import com.hefny.hady.marvelstudios.utils.ErrorUtils
import com.hefny.hady.marvelstudios.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val marvelApi: MarvelApi) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val _marvelSummariesMutableLiveData =
        MutableLiveData<Resource<ArrayList<MarvelSummary>>>()
    val marvelSummariesLiveData: LiveData<Resource<ArrayList<MarvelSummary>>>
        get() = _marvelSummariesMutableLiveData

    fun getPagingCharacters(name: String? = null): LiveData<PagingData<Character>> {
        return Pager(
            PagingConfig(20)
        ) {
            CharactersDataSource(marvelApi, name)
        }.liveData
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
        disposable.dispose()
    }
}