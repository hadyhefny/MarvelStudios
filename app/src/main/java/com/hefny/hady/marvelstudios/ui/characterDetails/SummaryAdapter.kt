package com.hefny.hady.marvelstudios.ui.characterDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.models.MarvelSummary
import kotlinx.android.synthetic.main.summary_list_item.view.*

class SummaryAdapter(private val summaryClickListener: SummaryClickListener) :
    RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {
    private var summaryList = ArrayList<MarvelSummary>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.summary_list_item, parent, false)
        return SummaryViewHolder(view, summaryClickListener)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val item = summaryList.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return summaryList.size
    }

    fun setSummaryList(summaryList: ArrayList<MarvelSummary>?) {
        summaryList?.let {
            this.summaryList = summaryList
            notifyDataSetChanged()
        }
    }

    class SummaryViewHolder(
        itemView: View,
        private val summaryClickListener: SummaryClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(summary: MarvelSummary) {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(60))
            Glide.with(itemView.context)
                .load(summary.thumbnail?.getImageUrl())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .apply(requestOptions)
                .into(itemView.summary_image_imageview)
            itemView.summary_image_imageview.transitionName = "${summary.name}$bindingAdapterPosition"
            if (!summary.name.isNullOrBlank()) {
                itemView.summary_name_textview.setText(summary.name)
            } else if (!summary.title.isNullOrBlank()) {
                itemView.summary_name_textview.setText(summary.title)
            }
            itemView.setOnClickListener {
                summaryClickListener.onSummaryCLicked(summary.resourceURI, itemView.summary_image_imageview)
            }
        }
    }

    interface SummaryClickListener {
        fun onSummaryCLicked(resourceUri: String, view: View)
    }
}