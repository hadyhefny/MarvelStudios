package com.hefny.hady.marvelstudios.ui.charactersList.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.hefny.hady.marvelstudios.api.MarvelApi
import com.hefny.hady.marvelstudios.api.responses.MainResponse
import com.hefny.hady.marvelstudios.models.Character
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CharactersDataSource(
    private val marvelApi: MarvelApi,
    private val name: String?
) : RxPagingSource<Int, Character>() {
    override val keyReuseSupported: Boolean
        get() = true

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Character>> {
        var nextOffset: Int? = params.key
        if (nextOffset == null) {
            nextOffset = 0
        }
        return marvelApi.getCharacters(name = name, offset = nextOffset)
            .subscribeOn(Schedulers.io())
            .map(this::toLoadResult)
            .onErrorReturn { t -> LoadResult.Error(t!!) }
    }

    private fun toLoadResult(
        response: MainResponse
    ): LoadResult<Int, Character> {
        return LoadResult.Page(
            response.data.results,
            null,
            response.data.getNextOffset(),
            LoadResult.Page.COUNT_UNDEFINED,
            LoadResult.Page.COUNT_UNDEFINED
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition: Int = state.anchorPosition ?: return null
        val anchorPage: LoadResult.Page<Int, Character> =
            state.closestPageToPosition(anchorPosition) ?: return null
        val prevKey: Int? = anchorPage.prevKey
        prevKey?.let {
            return it + 20
        }
        val nextKey: Int? = anchorPage.nextKey
        nextKey?.let {
            return it - 20
        }
        return null
    }
}