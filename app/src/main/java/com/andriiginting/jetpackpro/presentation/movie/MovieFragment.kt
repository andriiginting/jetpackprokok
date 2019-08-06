package com.andriiginting.jetpackpro.presentation.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andriiginting.jetpackpro.R
import com.andriiginting.jetpackpro.base.BaseAdapter
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity.Companion.MOVIE_KEY
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity.Companion.MOVIE_TYPE
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity.Companion.SCREEN_TYPE
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity.Companion.navigate
import com.andriiginting.jetpackpro.presentation.movie.viewmodel.MovieState
import com.andriiginting.jetpackpro.presentation.movie.viewmodel.MovieViewModel
import com.andriiginting.jetpackpro.utils.makeGone
import com.andriiginting.jetpackpro.utils.makeVisible
import com.andriiginting.jetpackpro.utils.setGridView
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {
    companion object {
        private const val GRID_COLUMN = 2
        fun newInstance(): MovieFragment = MovieFragment()
    }

    private lateinit var movieAdapter: BaseAdapter<MovieItem, MovieViewHolder>
    private var movieList: List<MovieItem>? = listOf()

    private lateinit var viewModel: MovieViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders
            .of(this)
            .get(MovieViewModel::class.java)
        initData()
        initObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupData()
    }

    private fun initData() {
        viewModel.getMovies()
    }

    private fun setupData() {
        rvMovieFragment.apply {
            setGridView(GRID_COLUMN)
            adapter = movieAdapter
        }
    }

    private fun initObserver() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is MovieState.ShowLoading -> showLoading()
                is MovieState.HideLoading -> hideLoading()
                is MovieState.LoadMovieSuccess -> {
                    loadData(state.data)
                    hideErrorScreen()
                }
                is MovieState.LoadMovieError -> {
                    hideLoading()
                    showErrorScreen()
                }
            }
        })
    }

    private fun loadData(items: MovieResponse) {
        movieAdapter.addAll(items.resultsIntent)
        movieList = items.resultsIntent
    }

    private fun showErrorScreen() = layoutError.makeVisible()

    private fun hideErrorScreen() = layoutError.makeGone()

    private fun showLoading() = pbMainMovie.makeVisible()

    private fun hideLoading() = pbMainMovie.makeGone()

    private fun navigateTo(position: Int) {
        startActivity(navigate(requireActivity())
            .apply {
                putExtra(MOVIE_KEY, movieList?.get(position))
                putExtra(SCREEN_TYPE, MOVIE_TYPE)
            }
        )
    }

    private fun setupAdapter() {
        movieAdapter = BaseAdapter({ parent, _ ->
            MovieViewHolder.inflate(parent).also {
                it.setPosterAction(::navigateTo)
            }
        }, { viewHolder, _, item ->
            viewHolder.setPoster(item.posterPath)
        })
    }
}
