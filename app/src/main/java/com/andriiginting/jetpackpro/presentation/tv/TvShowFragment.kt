package com.andriiginting.jetpackpro.presentation.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andriiginting.jetpackpro.R
import com.andriiginting.jetpackpro.TheaterBaseAdapter
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.TvItem
import com.andriiginting.jetpackpro.data.model.TvResponse
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity
import com.andriiginting.jetpackpro.presentation.module.InjectionModule
import com.andriiginting.jetpackpro.presentation.movie.MovieViewHolder
import com.andriiginting.jetpackpro.presentation.tv.viewmodel.TvShowViewModel
import com.andriiginting.jetpackpro.presentation.tv.viewmodel.TvShowViewModelFactory
import com.andriiginting.jetpackpro.presentation.tv.viewmodel.TvState
import com.andriiginting.jetpackpro.utils.makeGone
import com.andriiginting.jetpackpro.utils.makeVisible
import com.andriiginting.jetpackpro.utils.setGridView
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment() {
    companion object {
        private const val GRID_COLUMN = 2
        fun newInstance(): TvShowFragment = TvShowFragment()
    }

    private lateinit var tvAdapter: TheaterBaseAdapter<TvItem, MovieViewHolder>
    private var showList: List<TvItem>? = listOf()

    private lateinit var viewModel: TvShowViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders
            .of(this, TvShowViewModelFactory(InjectionModule.provideHomeRepository()))
            .get(TvShowViewModel::class.java)
        initData()
        initObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupData()
    }

    private fun initData() {
        viewModel.getTvShow()
    }

    private fun setupData() {
        rvTvFragment.apply {
            setGridView(GRID_COLUMN)
            adapter = tvAdapter
        }
    }

    private fun loadData(items: TvResponse) {
        tvAdapter.safeClearAndAddAll(items.resultsIntent)
        showList = items.resultsIntent
    }

    private fun setupAdapter() {
        tvAdapter = TheaterBaseAdapter({ parent, _ ->
            MovieViewHolder.inflate(parent).also {
                it.setPosterAction(::navigateTo)
            }
        }, { viewHolder, _, item ->
            viewHolder.setPoster(item.posterPath)
        })
    }

    private fun showLoading() = pbTvShow.makeVisible()

    private fun hideLoading() = pbTvShow.makeGone()

    private fun showErrorScreen() = layoutError.makeVisible()

    private fun hideErrorScreen() = layoutError.makeGone()

    private fun navigateTo(position: Int) {
        val item = showList?.get(position)
        startActivity(
            DetailScreenActivity.navigate(requireActivity())
                .apply {
                    putExtra(
                        DetailScreenActivity.MOVIE_KEY, MovieItem(
                            id = item?.id.orEmpty(),
                            movieId = item?.id.orEmpty(),
                            posterPath = item?.posterPath.orEmpty(),
                            overview = item?.overview.orEmpty(),
                            title = item?.title.orEmpty(),
                            backdropPath = item?.backdropPath.orEmpty(),
                            releaseDate = item?.releaseDate.orEmpty()
                        )
                    )
                    putExtra(DetailScreenActivity.SCREEN_TYPE, DetailScreenActivity.TV_TYPE)
                }
        )
    }

    private fun initObserver() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is TvState.ShowLoading -> showLoading()
                is TvState.HideLoading -> hideLoading()
                is TvState.LoadMovieSuccess -> {
                    loadData(state.data)
                    hideErrorScreen()
                }
                is TvState.LoadMovieError -> {
                    hideLoading()
                    showErrorScreen()
                }
            }
        })
    }
}
