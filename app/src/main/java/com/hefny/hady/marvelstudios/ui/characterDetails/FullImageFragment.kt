package com.hefny.hady.marvelstudios.ui.characterDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.ui.BaseFragment
import com.hefny.hady.marvelstudios.utils.Constants
import kotlinx.android.synthetic.main.fragment_full_image.*

class FullImageFragment : BaseFragment() {
    private val TAG = "AppDebug"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(Constants.FULL_IMAGE_KEY)?.let {
            viewModel.getMarvelSummaries(it, Constants.SINGLE_IMAGE)
        }
        viewModel.marvelSummariesLiveData.observe(viewLifecycleOwner, { imageResource ->
            // handle loading
            uiCommunicationListener.showProgressBar(imageResource.loading)
            // handle success
            if (imageResource.type == Constants.SINGLE_IMAGE) {
                imageResource.data?.getContentIfNotHandled()?.let { marvelSummaryList ->
                    if (marvelSummaryList[0].thumbnail != null) {
                        var requestOptions = RequestOptions()
                        requestOptions = requestOptions.transform(RoundedCorners(60))
                        Glide.with(requireContext())
                            .load(marvelSummaryList[0].thumbnail?.getImageUrl())
                            .placeholder(R.drawable.image_placeholder)
                            .error(R.drawable.image_placeholder)
                            .apply(requestOptions)
                            .into(full_image)
                    } else {
                        parentFragmentManager.popBackStack()
                    }
                }
            }
            // handle error
            imageResource.error?.peekContent()?.let {
                Log.d(TAG, "onViewCreated: error message: $it")
                parentFragmentManager.popBackStack()
                uiCommunicationListener.showError(it)
            }
        })
    }
}