package com.hefny.hady.marvelstudios.ui.searchCharacters

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.api.responses.ErrorResponse
import com.hefny.hady.marvelstudios.ui.BaseFragment
import com.hefny.hady.marvelstudios.ui.CharactersLoadStateAdapter
import com.hefny.hady.marvelstudios.ui.characterDetails.CharacterDetailsFragment
import com.hefny.hady.marvelstudios.utils.Constants
import com.hefny.hady.marvelstudios.utils.ErrorUtils
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_characters_list.*
import kotlinx.android.synthetic.main.fragment_search_characters.*
import java.util.concurrent.TimeUnit

class SearchCharactersFragment : BaseFragment(),
    SearchCharactersPagingAdapter.CharacterClickListener {
    private lateinit var pagingAdapter: SearchCharactersPagingAdapter
    private val disposable = CompositeDisposable()
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.slide_left)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
        handler = Handler(Looper.getMainLooper())
        searchCharactersByName()
        pagingAdapter.addLoadStateListener { loadStates ->
            // handle different loading states (error, loading) when first loading the list
            when (loadStates.refresh) {
                is LoadState.Loading -> uiCommunicationListener.showProgressBar(true)
                is LoadState.NotLoading -> uiCommunicationListener.showProgressBar(false)
                is LoadState.Error -> {
                    uiCommunicationListener.showProgressBar(false)
                    val errorResponse: ErrorResponse =
                        ErrorUtils.parseError((loadStates.refresh as LoadState.Error).error)
                    uiCommunicationListener.showError(errorResponse.message)
                }
            }
        }
        cancel_text.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun searchCharactersByName() {
        // create an observable with debounce
        val observableText = Observable
            .create(ObservableOnSubscribe<String> { emitter ->
                // listen to edit text changes
                search_edittext.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        // emit text to the observer
                        if (!emitter.isDisposed) {
                            emitter.onNext(s.toString())
                        }
                        // clear adapter if search text is empty
                        if (s.toString().isBlank()) {
                            pagingAdapter.submitData(
                                requireActivity().lifecycle,
                                PagingData.empty()
                            )
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }
                })
            })
            .debounce(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())

        // listen to emits
        observableText.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                disposable.add(d)
            }

            override fun onNext(t: String?) {
                t?.let { query ->
                    // make sure result is observed in main thread
                    handler.post {
                        handleSearchResult(query)
                    }
                }
            }

            override fun onError(e: Throwable?) {
            }

            override fun onComplete() {
            }
        })
    }

    private fun handleSearchResult(name: String) {
        if (name.isNotBlank()) {
            pagingAdapter.setTextToHighlight(name)
            view?.let {
                viewModel.getPagingCharacters(name).observe(viewLifecycleOwner, { pagingData ->
                    pagingAdapter.submitData(this@SearchCharactersFragment.lifecycle, pagingData)
                    pagingAdapter.notifyDataSetChanged()
                })
            }
        } else {
            pagingAdapter.submitData(
                requireActivity().lifecycle,
                PagingData.empty()
            )
        }
    }

    private fun initRecyclerview() {
        search_characters_recyclerview.run {
            layoutManager = LinearLayoutManager(requireContext())
            pagingAdapter = SearchCharactersPagingAdapter(this@SearchCharactersFragment)
            adapter = pagingAdapter.withLoadStateFooter(CharactersLoadStateAdapter())
        }
        search_characters_recyclerview.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                uiCommunicationListener.hideKeyboard()
            }
        })
    }

    override fun onCharacterCLicked(position: Int) {
        val characterDetailsFragment = CharacterDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable(Constants.CHARACTER_KEY, pagingAdapter.peek(position))
        characterDetailsFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .add(R.id.main_container, characterDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}