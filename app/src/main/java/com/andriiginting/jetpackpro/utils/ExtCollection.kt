package com.andriiginting.jetpackpro.utils

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.jetpackpro.BuildConfig
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun RecyclerView.setGridView(numberOfColumns: Int): RecyclerView {
    layoutManager = GridLayoutManager(context, numberOfColumns)
    return this
}

fun <T> singleIo(): SingleTransformer<T, T> {
    return SingleTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> flowableIo(): FlowableTransformer<T, T> {
    return FlowableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun completeIo(): CompletableTransformer {
    return CompletableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

infix fun CompositeDisposable.plus(d: Disposable?): CompositeDisposable {
    d?.let(::add)
    return this
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(BuildConfig.MOVIE_IMAGE_URL + url)
        .centerCrop()
        .into(this)
}

fun showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun MovieItem.mapToTheater(type: String): TheaterFavorite {
    return TheaterFavorite(
        theaterFavoriteId = this.id,
        theaterTitle = this.title,
        posterPath = this.posterPath,
        overview = this.overview,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        type = type
    )
}

fun TheaterFavorite.mapToMovie(): MovieItem {
    return MovieItem(
        id = this.theaterFavoriteId.orEmpty(),
        movieId = this.theaterFavoriteId.orEmpty(),
        posterPath = this.posterPath.orEmpty(),
        overview = this.overview.orEmpty(),
        title = this.theaterTitle.orEmpty(),
        backdropPath = this.backdropPath.orEmpty(),
        releaseDate = this.releaseDate.orEmpty()
    )
}