package com.andriiginting.jetpackpro.presentation.TheaterDetailScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andriiginting.jetpackpro.R
import com.andriiginting.jetpackpro.base.BaseActivity
import com.andriiginting.jetpackpro.base.BaseAdapter
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.presentation.module.InjectionModule
import com.andriiginting.jetpackpro.presentation.movie.MovieViewHolder
import com.andriiginting.jetpackpro.utils.*
import kotlinx.android.synthetic.main.activity_detail_screen.*

class DetailScreenActivity : BaseActivity() {
    companion object {
        const val SCREEN_TYPE = "screenType"
        const val MOVIE_KEY = "movieKey"
        const val MOVIE_TYPE = "Movie"
        const val TV_TYPE = "Tv Show"
        private const val GRID_COLUMN = 3

        var detailIdle = 1
        fun navigate(activity: Activity): Intent = Intent(activity, DetailScreenActivity::class.java)
    }

    private lateinit var movieAdapter: BaseAdapter<MovieItem, MovieViewHolder>
    private var movieSimilar: List<MovieItem>? = listOf()

    private lateinit var vm: TheaterDetailViewModel
    private lateinit var screenType: String
    private var isFavoriteTheater = false
    private var data: MovieItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders
            .of(this, TheaterDetailViewModelFactory(InjectionModule.provideTheaterDatabase(applicationContext)))
            .get(TheaterDetailViewModel::class.java)
        initObserver()

        screenType = intent.getStringExtra(SCREEN_TYPE).orEmpty()
        data = intent.getParcelableExtra(MOVIE_KEY)
    }

    override fun getLayoutId(): Int = R.layout.activity_detail_screen
    override fun setupView() {
        initData()
        setupDetailScreen()
    }

    override fun onResume() {
        super.onResume()
        setupSimilarScreen()
    }

    private fun initData() {
        setupAdapter()
        vm.isFavoriteTheater(data?.id.orEmpty())
        when (screenType) {
            MOVIE_TYPE -> vm.getSimilarMovie(data?.id.orEmpty())
            TV_TYPE -> vm.getSimilarTv(data?.id.orEmpty())
        }
    }

    private fun setupDetailScreen() {
        data.let { data ->
            tvMovieTitle.text = data?.title.orEmpty()
            tvMovieDescription.text = data?.overview.orEmpty()
        }

        ivBackNavigation.setOnClickListener {
            onBackPressed()
            finish()
        }
        ivPosterBackdrop.loadImage(data?.backdropPath.orEmpty())
        ivTheaterFavorite.setOnClickListener { clickAction(isFavoriteTheater) }

    }

    private fun clickAction(isFavorite: Boolean) {
        if (isFavorite) {
            data?.let {
                vm.deleteTheaterData(it.movieId)
            }
        } else {
            data?.let { vm.saveFavoriteTheater(it, screenType) }
        }
    }

    private fun setupSimilarScreen() {
        rvSimilarMovie.apply {
            setGridView(GRID_COLUMN)
            adapter = movieAdapter
        }
    }

    private fun setupAdapter() {
        movieAdapter = BaseAdapter({ parent, _ ->
            MovieViewHolder.inflate(parent)
        }, { viewHolder, _, item ->
            viewHolder.setPoster(item.posterPath)
        })
    }

    private fun setupFavoriteButton(isFavorite: Boolean) {
        when {
            isFavorite -> {
                ivTheaterFavorite.apply {
                    setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_active, null))
                }
            }
            else -> {
                ivTheaterFavorite.apply {
                    setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_inactive, null))
                }
            }
        }
    }

    private fun loadData(items: MovieResponse) {
        movieAdapter.safeClearAndAddAll(items.resultsIntent)
        movieSimilar = items.resultsIntent
    }

    private fun showErrorScreen() = layoutError.makeVisible()

    private fun hideErrorScreen() = layoutError.makeGone()

    private fun initObserver() {
        vm.state.observe(this, Observer { state ->
            when (state) {
                is DetailScreenState.HideLoading -> {
                    pbDetailScreen.makeGone()
                }
                is DetailScreenState.ShowLoading -> {
                    pbDetailScreen.makeVisible()
                }
                is DetailScreenState.LoadScreenError -> {
                    pbDetailScreen.makeGone()
                    showErrorScreen()
                }
                is DetailScreenState.LoadSimilarMovieSuccess -> {
                    loadData(state.data)
                    hideErrorScreen()
                }
                is DetailScreenState.LoadSimilarTVSuccess -> {
                    loadData(state.data)
                    hideErrorScreen()
                }

                is DetailScreenState.SuccessToSaveData -> {
                    isFavoriteTheater = true
                    setupFavoriteButton(isFavoriteTheater)
                    showSnackBar(detailContainer, getString(R.string.text_success_save_data))
                }
                is DetailScreenState.FailedToSaveData -> {
                    showSnackBar(detailContainer, getString(R.string.text_failed_save_data))
                }

                is DetailScreenState.IsFavoriteTheater -> {
                    isFavoriteTheater = state.status
                    setupFavoriteButton(state.status)
                }

                is DetailScreenState.SuccessDeleteTheater -> {
                    isFavoriteTheater = false
                    setupFavoriteButton(isFavoriteTheater)
                    showSnackBar(detailContainer, getString(R.string.text_success_delete_data))
                }

                is DetailScreenState.FailedDeleteTheater -> {
                    showSnackBar(detailContainer, getString(R.string.text_success_delete_data))
                }
            }
        })
    }
}
