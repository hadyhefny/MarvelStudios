package com.hefny.hady.marvelstudios.ui.characterDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.models.Character
import com.hefny.hady.marvelstudios.ui.BaseFragment
import com.hefny.hady.marvelstudios.utils.Constants
import kotlinx.android.synthetic.main.fragment_character_details.*
import kotlinx.android.synthetic.main.search_character_list_item.view.*

class CharacterDetailsFragment : BaseFragment(), SummaryAdapter.SummaryClickListener {
    private val TAG = "AppDebug"
    private lateinit var comicsAdapter: SummaryAdapter
    private lateinit var eventsAdapter: SummaryAdapter
    private lateinit var seriesAdapter: SummaryAdapter
    private lateinit var storiesAdapter: SummaryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        arguments?.let { bundle ->
            val character: Character? = bundle.getParcelable(Constants.CHARACTER_KEY)
            setupViews(character)
        }
        character_details_back_constraintlayout.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupViews(character: Character?) {
        character?.let {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(
                CenterCrop(), GranularRoundedCorners(
                    0f,
                    0f,
                    60f,
                    60f
                )
            )
            Glide.with(requireContext())
                .load(it.thumbnail.getImageUrl())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .apply(requestOptions)
                .into(character_details_image_imageview)
            character_details_name_textview.setText(it.name)
            if (it.description.isBlank()) {
                character_details_description_label_textview.visibility = View.GONE
                character_details_description_textview.visibility = View.GONE
            } else {
                character_details_description_label_textview.visibility = View.VISIBLE
                character_details_description_textview.visibility = View.VISIBLE
            }
            character_details_description_textview.setText(it.description)
            comicsAdapter.setSummaryList(it.comics.items)
            if (it.events.items.size == 0) {
                character_details_events_label_textview.visibility = View.GONE
            } else {
                character_details_events_label_textview.visibility = View.VISIBLE
            }
            eventsAdapter.setSummaryList(it.events.items)
            seriesAdapter.setSummaryList(it.series.items)
            storiesAdapter.setSummaryList(it.stories.items)
            getImages(it)
        }
    }

    private fun getImages(character: Character) {
        getComicsImages(character)
        getEventsImages(character)
        getSeriesImages(character)
        getStoriesImages(character)
    }

    private fun getComicsImages(character: Character) {
        viewModel.getComicImage(character.comics.collectionURI)
            .observe(viewLifecycleOwner, { imageResource ->
                // handle loading
//            imageResource.loading
                // handle success
                imageResource.data?.peekContent()?.let { comicsList ->
                    Log.d(TAG, "getComicsImages: called")
                    comicsAdapter.setMarvelIssues(comicsList)
                }
                // handle error
                imageResource.error?.getContentIfNotHandled()?.let { errorMessage ->
                    Log.d(TAG, "onViewCreated: error message: $errorMessage")
                }
            })
    }

    private fun getEventsImages(character: Character) {
        viewModel.getEventImage(character.events.collectionURI)
            .observe(viewLifecycleOwner, { imageResource ->
                // handle loading
//            imageResource.loading
                // handle success
                imageResource.data?.peekContent()?.let { eventsList ->
                    eventsAdapter.setMarvelIssues(eventsList)
                }
                // handle error
                imageResource.error?.getContentIfNotHandled()?.let { errorMessage ->
                    Log.d(TAG, "onViewCreated: error message: $errorMessage")
                }
            })
    }

    private fun getSeriesImages(character: Character) {
        viewModel.getSeriesImage(character.series.collectionURI)
            .observe(viewLifecycleOwner, { imageResource ->
                // handle loading
//            imageResource.loading
                // handle success
                imageResource.data?.peekContent()?.let {
                    seriesAdapter.setMarvelIssues(it)
                }
                // handle error
                imageResource.error?.getContentIfNotHandled()?.let { errorMessage ->
                    Log.d(TAG, "onViewCreated: error message: $errorMessage")
                }
            })

    }

    private fun getStoriesImages(character: Character) {
        viewModel.getStoryImage(character.stories.collectionURI)
            .observe(viewLifecycleOwner, { imageResource ->
                // handle loading
//            imageResource.loading
                // handle success
                imageResource.data?.peekContent()?.let {
                    storiesAdapter.setMarvelIssues(it)
                }
                // handle error
                imageResource.error?.getContentIfNotHandled()?.let { errorMessage ->
                    Log.d(TAG, "onViewCreated: error message: $errorMessage")
                }
            })
    }

    private fun initRecyclerViews() {
        initComicsRecycler()
        initEventsRecycler()
        initSeriesRecycler()
        initStoriesRecycler()
    }

    private fun initComicsRecycler() {
        character_details_comics_recyclerview?.run {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            comicsAdapter = SummaryAdapter(this@CharacterDetailsFragment)
            adapter = comicsAdapter
        }
    }

    private fun initEventsRecycler() {
        character_details_events_recyclerview?.run {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            eventsAdapter = SummaryAdapter(this@CharacterDetailsFragment)
            adapter = eventsAdapter
        }
    }

    private fun initSeriesRecycler() {
        character_details_series_recyclerview?.run {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            seriesAdapter = SummaryAdapter(this@CharacterDetailsFragment)
            adapter = seriesAdapter
        }
    }

    private fun initStoriesRecycler() {
        character_details_stories_recyclerview?.run {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            storiesAdapter = SummaryAdapter(this@CharacterDetailsFragment)
            adapter = storiesAdapter
        }
    }

    override fun onSummaryCLicked(resourceUri: String) {
        Log.d(TAG, "onSummaryCLicked: $resourceUri")
        val fullImageFragment = FullImageFragment()
        val bundle = Bundle()
        bundle.putString(Constants.FULL_IMAGE_KEY, resourceUri)
        fullImageFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .add(R.id.main_container, fullImageFragment)
            .addToBackStack(null)
            .commit()
    }
}