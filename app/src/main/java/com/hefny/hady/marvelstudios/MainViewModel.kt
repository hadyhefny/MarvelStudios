package com.hefny.hady.marvelstudios

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hefny.hady.marvelstudios.api.ServiceGenerator
import com.hefny.hady.marvelstudios.api.responses.CharacterDataContainerResponse
import com.hefny.hady.marvelstudios.api.responses.ErrorResponse
import com.hefny.hady.marvelstudios.api.responses.MainResponse
import com.hefny.hady.marvelstudios.utils.ErrorUtils
import com.hefny.hady.marvelstudios.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val disposable = CompositeDisposable()
    private var _characterMutableLiveData =
        MutableLiveData<Resource<CharacterDataContainerResponse>>()
    private val TAG = "AppDebug"
    val charactersLiveData: LiveData<Resource<CharacterDataContainerResponse>>
        get() = _characterMutableLiveData

    init {
        getCharacters()
    }

    private fun getCharacters() {
        _characterMutableLiveData.value = Resource.Loading()
        ServiceGenerator.marvelApi.getCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<MainResponse> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.addAll(d)
                }

                override fun onSuccess(t: MainResponse?) {
                    _characterMutableLiveData.value = Resource.Success(t?.data)
                }

                override fun onError(e: Throwable?) {
                    Log.e(TAG, "onError: ", e)
                    val errorResponse: ErrorResponse = ErrorUtils.parseError(e)
                    _characterMutableLiveData.value = Resource.Error(errorResponse.message)
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}