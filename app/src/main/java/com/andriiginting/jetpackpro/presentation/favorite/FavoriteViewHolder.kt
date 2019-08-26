package com.andriiginting.jetpackpro.presentation.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.jetpackpro.R
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.utils.loadImage
import kotlinx.android.synthetic.main.item_favorite_theater.view.*

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun inflate(parent: ViewGroup): FavoriteViewHolder {
            return FavoriteViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_favorite_theater, parent, false)
            )
        }
    }

    private val poster = view.ivFavoriteTheater
    private val screenType = view.tvScreenType

    fun setPoster(url: String?) {
        poster.loadImage(url.orEmpty())
    }

    fun setScreenType(type: String) {
        screenType.text = type
    }

}