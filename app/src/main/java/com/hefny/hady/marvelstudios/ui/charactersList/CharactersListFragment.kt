package com.hefny.hady.marvelstudios.ui.charactersList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_characters_list.*

class CharactersListFragment : BaseFragment() {
    private val TAG = "AppDebug"
    private lateinit var charactersAdapter: CharactersListAdapter
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
        viewModel.charactersLiveData.observe(viewLifecycleOwner, { dataResource ->
            // handle loading
            loadingStateListener.showLoadingState(dataResource.loading)
            // handle success
            dataResource.data?.peekContent()?.results?.let { charactersList ->
                Log.d(TAG, "onCreate: $charactersList")
                charactersAdapter.setCharacters(charactersList)
            }
            // handle error
            dataResource.error?.getContentIfNotHandled()?.let { errorMessage ->
                Log.d(TAG, "onCreate: error: $errorMessage")
            }
        })
    }

    private fun initRecyclerview() {
        characters_recyclerview.run {
            layoutManager = LinearLayoutManager(requireContext())
            charactersAdapter = CharactersListAdapter()
            adapter = charactersAdapter
        }
    }
}