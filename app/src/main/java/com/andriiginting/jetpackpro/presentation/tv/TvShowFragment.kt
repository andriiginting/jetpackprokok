package com.andriiginting.jetpackpro.presentation.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.andriiginting.jetpackpro.R
import com.andriiginting.jetpackpro.base.BaseAdapter
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.TvItem
import com.andriiginting.jetpackpro.data.model.TvResponse
import com.andriiginting.jetpackpro.presentation.movie.MovieViewHolder
import com.andriiginting.jetpackpro.presentation.movie.viewmodel.MovieState
import com.andriiginting.jetpackpro.presentation.movie.viewmodel.MovieViewModel
import com.andriiginting.jetpackpro.presentation.tv.viewmodel.TvShowViewModel
import com.andriiginting.jetpackpro.presentation.tv.viewmodel.TvState
import com.andriiginting.jetpackpro.utils.gone
import com.andriiginting.jetpackpro.utils.setGridView
import com.andriiginting.jetpackpro.utils.visible
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment() {
    companion object {
        private const val GRID_COLUMN = 2
        fun newInstance(): TvShowFragment = TvShowFragment()
    }

    private lateinit var tvAdapter: BaseAdapter<TvItem, MovieViewHolder>
    private var showList: List<TvItem>? = listOf()

    private lateinit var viewModel: TvShowViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =ViewModelProviders
            .of(this)
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

    private fun initData(){
        viewModel.getTvShow()
    }

    private fun setupData(){
        rvTvFragment.apply {
            setGridView(GRID_COLUMN)
            adapter = tvAdapter
        }
    }

    private fun loadData(items: TvResponse){
        tvAdapter.addAll(items.resultsIntent)
        showList = items.resultsIntent
    }

    private fun setupAdapter(){
        tvAdapter = BaseAdapter({ parent, _ ->
            MovieViewHolder.inflate(parent)
        }, { viewHolder, _, item ->
            viewHolder.setPoster(item.posterPath)
        })
    }

    private fun showLoading() = pbTvShow.visible()

    private fun hideLoading() = pbTvShow.gone()

    private fun initObserver(){
        viewModel.state.observe(this, Observer { state ->
            when(state) {
                is TvState.ShowLoading -> showLoading()
                is TvState.HideLoading -> hideLoading()
                is TvState.LoadMovieSuccess -> loadData(state.data)
                is TvState.LoadMovieError -> {
                    hideLoading()
                }
            }
        })
    }
}
