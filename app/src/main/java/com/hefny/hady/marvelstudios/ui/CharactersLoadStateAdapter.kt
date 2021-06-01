package com.hefny.hady.marvelstudios.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hefny.hady.marvelstudios.R
import com.hefny.hady.marvelstudios.utils.ErrorUtils
import kotlinx.android.synthetic.main.loading_list_item.view.*

class CharactersLoadStateAdapter :
    LoadStateAdapter<CharactersLoadStateAdapter.LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.loading_list_item, parent, false)
        return LoadStateViewHolder(view)
    }

    class LoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(loadState: LoadState) {
            itemView.progressDialog_list_item.isVisible = loadState is LoadState.Loading
            itemView.error_list_item.isVisible = loadState is LoadState.Error
            if (loadState is LoadState.Error) {
                val errorResponse = ErrorUtils.parseError(loadState.error)
                itemView.error_list_item.setText(errorResponse.message)
            }
        }
    }
}