package com.andriiginting.jetpackpro.base

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.presentation.favorite.FavoriteViewHolder

typealias ActionListener = (TheaterFavorite) -> Unit

class TheaterPagingAdapter(
    private val actionListener: ActionListener
) : PagedListAdapter<TheaterFavorite, FavoriteViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TheaterFavorite>() {
            override fun areItemsTheSame(
                oldItem: TheaterFavorite,
                newItem: TheaterFavorite
            ): Boolean =
                oldItem.theaterFavoriteId == newItem.theaterFavoriteId

            override fun areContentsTheSame(
                oldItem: TheaterFavorite,
                newItem: TheaterFavorite
            ): Boolean =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = getItem(position)

        holder.apply {
            setPoster(favorite?.posterPath)
            setScreenType(favorite?.type.toString())

            itemView.setOnClickListener {
                favorite?.let(actionListener::invoke)
            }
        }

    }
}