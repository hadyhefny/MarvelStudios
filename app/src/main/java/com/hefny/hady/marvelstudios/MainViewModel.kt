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
import com.hefny.hady.marvelstudios.models.MarvelIssue
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
    private val _imageMutableLiveData = MutableLiveData<Resource<MarvelIssue>>()
    private val _imageComicMutableLiveData = MutableLiveData<Resource<ArrayList<MarvelIssue>>>()
    private val _imageEventMutableLiveData = MutableLiveData<Resource<ArrayList<MarvelIssue>>>()
    private val _imageSeriesMutableLiveData = MutableLiveData<Resource<ArrayList<MarvelIssue>>>()
    private val _imageStoryMutableLiveData = MutableLiveData<Resource<ArrayList<MarvelIssue>>>()
    fun getPagingCharacters(name: String? = null): LiveData<PagingData<Character>> {
        return Pager(
            PagingConfig(20)
        ) {
            CharactersDataSource(marvelApi, name)
        }.liveData
    }

    fun getImage(url: String): LiveData<Resource<MarvelIssue>> {
        _imageMutableLiveData.value = Resource.loading(true)
        marvelApi.getImage(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                SingleObserver<MainResponse<DataContainerResponse<MarvelIssue>>> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onSuccess(t: MainResponse<DataContainerResponse<MarvelIssue>>?) {
                    t?.data?.results?.let {
                        _imageMutableLiveData.value = Resource.data(it[0])
                    }
                }

                override fun onError(e: Throwable?) {
                    _imageMutableLiveData.value =
                        Resource.error(ErrorUtils.parseError(e).message)
                }
            })
        return _imageMutableLiveData
    }

    fun getComicImage(url: String): LiveData<Resource<ArrayList<MarvelIssue>>> {
        _imageComicMutableLiveData.value = Resource.loading(true)
        marvelApi.getImage(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                SingleObserver<MainResponse<DataContainerResponse<MarvelIssue>>> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onSuccess(t: MainResponse<DataContainerResponse<MarvelIssue>>?) {
                    t?.data?.results?.let {
                        _imageComicMutableLiveData.value = Resource.data(it)
                    }
                }

                override fun onError(e: Throwable?) {
                    _imageComicMutableLiveData.value =
                        Resource.error(ErrorUtils.parseError(e).message)
                }
            })
        return _imageComicMutableLiveData
    }

    fun getEventImage(url: String): LiveData<Resource<ArrayList<MarvelIssue>>> {
        _imageEventMutableLiveData.value = Resource.loading(true)
        marvelApi.getImage(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                SingleObserver<MainResponse<DataContainerResponse<MarvelIssue>>> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onSuccess(t: MainResponse<DataContainerResponse<MarvelIssue>>?) {
                    t?.data?.results?.let {
                        _imageEventMutableLiveData.value = Resource.data(it)
                    }
                }

                override fun onError(e: Throwable?) {
                    _imageEventMutableLiveData.value =
                        Resource.error(ErrorUtils.parseError(e).message)
                }
            })
        return _imageEventMutableLiveData
    }

    fun getSeriesImage(url: String): LiveData<Resource<ArrayList<MarvelIssue>>> {
        _imageSeriesMutableLiveData.value = Resource.loading(true)
        marvelApi.getImage(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                SingleObserver<MainResponse<DataContainerResponse<MarvelIssue>>> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onSuccess(t: MainResponse<DataContainerResponse<MarvelIssue>>?) {
                    t?.data?.results?.let {
                        _imageSeriesMutableLiveData.value = Resource.data(it)
                    }
                }

                override fun onError(e: Throwable?) {
                    _imageSeriesMutableLiveData.value =
                        Resource.error(ErrorUtils.parseError(e).message)
                }
            })
        return _imageSeriesMutableLiveData
    }

    fun getStoryImage(url: String): LiveData<Resource<ArrayList<MarvelIssue>>> {
        _imageStoryMutableLiveData.value = Resource.loading(true)
        marvelApi.getImage(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                SingleObserver<MainResponse<DataContainerResponse<MarvelIssue>>> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onSuccess(t: MainResponse<DataContainerResponse<MarvelIssue>>?) {
                    t?.data?.results?.let {
                        _imageStoryMutableLiveData.value = Resource.data(it)
                    }
                }

                override fun onError(e: Throwable?) {
                    _imageStoryMutableLiveData.value =
                        Resource.error(ErrorUtils.parseError(e).message)
                }
            })
        return _imageStoryMutableLiveData
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}