package com.andriiginting.jetpackpro.presentation.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.jetpackpro.BuildConfig
import com.andriiginting.jetpackpro.R
import com.andriiginting.jetpackpro.utils.loadImage
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_movie_item_component.view.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun inflate(parent: ViewGroup): MovieViewHolder {
            return MovieViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_movie_item_component, parent, false)
            )
        }
    }

    private val poster = view.ivPoster

    fun setPoster(url: String?) {
        Glide.with(itemView.context)
            .load("${BuildConfig.MOVIE_IMAGE_URL}$url")
            .into(poster)
    }

    fun setPosterAction(action: (position: Int) -> Unit) {
        poster.setOnClickListener { action.invoke(adapterPosition) }
    }
}