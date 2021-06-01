package com.hefny.hady.marvelstudios.ui.charactersList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.api.responses.ErrorResponse
import com.hefny.hady.marvelstudios.ui.BaseFragment
import com.hefny.hady.marvelstudios.ui.characterDetails.CharacterDetailsFragment
import com.hefny.hady.marvelstudios.ui.searchCharacters.SearchCharactersFragment
import com.hefny.hady.marvelstudios.utils.Constants
import com.hefny.hady.marvelstudios.utils.ErrorUtils
import kotlinx.android.synthetic.main.fragment_characters_list.*

class CharactersListFragment : BaseFragment(), CharactersPagingAdapter.CharacterClickListener {
    private val TAG = "AppDebug"
    private lateinit var pagingAdapter: CharactersPagingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: viewmodel hash: ${viewModel.hashCode()}")
        initRecyclerview()
        // handle success
        viewModel.getPagingCharacters().observe(viewLifecycleOwner, {
            pagingAdapter.submitData(this.lifecycle, it)
        })
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
                    Log.d(TAG, "onViewCreated: errorMessage: ${errorResponse.message}")
                }
            }
//            // handle different loading states (error, loading) when try to paginate
//            when (loadStates.append) {
//                is LoadState.Loading -> loadingStateListener.showLoadingState(true)
//                is LoadState.NotLoading -> loadingStateListener.showLoadingState(false)
//                is LoadState.Error -> {
//                    loadingStateListener.showLoadingState(false)
//                    val errorResponse: ErrorResponse =
//                        ErrorUtils.parseError((loadStates.append as LoadState.Error).error)
//                    Log.d(TAG, "onViewCreated: errorMessage: ${errorResponse.message}")
//                }
//            }
        }
        search_icon_imageview.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.main_container, SearchCharactersFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun initRecyclerview() {
        characters_recyclerview.run {
            layoutManager = LinearLayoutManager(requireContext())
            pagingAdapter = CharactersPagingAdapter(this@CharactersListFragment)
            adapter = pagingAdapter
        }
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
}