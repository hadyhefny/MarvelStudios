package com.hefny.hady.marvelstudios.ui.charactersList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.models.Character
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharactersPagingAdapter(private val characterClickListener: CharacterClickListener) :
    PagingDataAdapter<Character, CharactersPagingAdapter.CharactersViewHolder>(DiffCallback) {

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
            .inflate(R.layout.character_list_item, parent, false)
        return CharactersViewHolder(view, characterClickListener)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CharactersViewHolder(
        itemView: View,
        private val characterClickListener: CharacterClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character?) {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(60))
            Glide.with(itemView.context)
                .load(character?.thumbnail?.getImageUrl())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .apply(requestOptions)
                .into(itemView.character_image_imageview)
            val imageTransitionName = "charactersListTransitionAnimation"
            itemView.character_image_imageview.transitionName =
                "$imageTransitionName$bindingAdapterPosition"
            itemView.character_name_textview.setText(character?.name)
            itemView.setOnClickListener {
                characterClickListener.onCharacterCLicked(
                    bindingAdapterPosition,
                    itemView.character_image_imageview
                )
            }
        }
    }

    interface CharacterClickListener {
        fun onCharacterCLicked(position: Int, view: View)
    }
}