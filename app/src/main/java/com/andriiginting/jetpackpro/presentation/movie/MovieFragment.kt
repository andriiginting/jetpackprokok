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
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity.Companion.navigate
import com.andriiginting.jetpackpro.presentation.movie.viewmodel.MovieState
import com.andriiginting.jetpackpro.presentation.movie.viewmodel.MovieViewModel
import com.andriiginting.jetpackpro.utils.gone
import com.andriiginting.jetpackpro.utils.setGridView
import com.andriiginting.jetpackpro.utils.visible
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
                is MovieState.LoadMovieSuccess -> loadData(state.data)
                is MovieState.LoadMovieError -> {
                    hideLoading()
                }
            }
        })
    }

    private fun loadData(items: MovieResponse) {
        movieAdapter.addAll(items.resultsIntent)
        movieList = items.resultsIntent
    }

    private fun showLoading() = pbMainMovie.visible()

    private fun hideLoading() = pbMainMovie.gone()

    private fun navigateTo(pos: Int) {
        startActivity(navigate(requireActivity())
            .apply { putExtra(MOVIE_KEY, pos) }
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
