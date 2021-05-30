package com.hefny.hady.marvelstudios.ui.searchCharacters

import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.models.Character
import kotlinx.android.synthetic.main.search_character_list_item.view.*


class SearchCharactersPagingAdapter(private val characterClickListener: CharacterClickListener) :
    PagingDataAdapter<Character, SearchCharactersPagingAdapter.CharactersViewHolder>(DiffCallback) {

    private var textToHighlight: String = ""

    private object DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_character_list_item, parent, false)
        return CharactersViewHolder(view, characterClickListener)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, textToHighlight)
    }

    fun setTextToHighlight(textToHighlight: String){
        this.textToHighlight = textToHighlight
    }

    class CharactersViewHolder(
        itemView: View,
        private val characterClickListener: CharacterClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character?, textToHighlight: String) {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(
                CenterCrop(), GranularRoundedCorners(
                    60f,
                    0f,
                    0f,
                    60f
                )
            )
            Glide.with(itemView.context)
                .load(character?.thumbnail?.getImageUrl())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .apply(requestOptions)
                .into(itemView.search_character_image_imageview)

            itemView.search_character_name_textview.setText(character?.name)
            setHighLightedText(itemView.search_character_name_textview, textToHighlight)
            itemView.setOnClickListener {
                characterClickListener.onCharacterCLicked(bindingAdapterPosition)
            }
        }

        private fun setHighLightedText(tv: TextView, textToHighlight: String) {
            val tvt = tv.text.toString()
            var ofe = tvt.indexOf(textToHighlight, 0, true)
            val wordToSpan: Spannable = SpannableString(tv.text)
            var ofs = 0
            while (ofs < tvt.length && ofe != -1) {
                ofe = tvt.indexOf(textToHighlight, ofs, true)
                if (ofe == -1) break else {
                    // set color here
                    wordToSpan.setSpan(
                        BackgroundColorSpan(ContextCompat.getColor(itemView.context, R.color.red)),
                        ofe,
                        ofe + textToHighlight.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    tv.setText(wordToSpan, TextView.BufferType.SPANNABLE)
                }
                ofs = ofe + 1
            }
        }
    }

    interface CharacterClickListener {
        fun onCharacterCLicked(position: Int)
    }
}