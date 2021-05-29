package com.hefny.hady.marvelstudios.ui.charactersList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.models.Character
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharactersListAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var charactersList = ArrayList<Character>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CharactersViewHolder).bind(charactersList[position])
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }

    fun setCharacters(characters: ArrayList<Character>) {
        charactersList = characters
        notifyDataSetChanged()
    }

    private class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character) {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(60))
            Glide.with(itemView.context)
                .load(character.thumbnail.getImageUrl())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .apply(requestOptions)
                .into(itemView.character_image_imageview)

            itemView.character_name_textview.setText(character.name)
        }
    }
}