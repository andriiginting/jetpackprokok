package com.andriiginting.jetpackpro.presentation.favorite


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.andriiginting.jetpackpro.R
import com.andriiginting.jetpackpro.base.ActionListener
import com.andriiginting.jetpackpro.base.TheaterPagingAdapter
import com.andriiginting.jetpackpro.data.database.TheaterDatabase
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.data.repository.FavoriteRepositoryImpl
import com.andriiginting.jetpackpro.domain.TheaterUseCase
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity.Companion.MOVIE_KEY
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity.Companion.MOVIE_TYPE
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity.Companion.SCREEN_TYPE
import com.andriiginting.jetpackpro.presentation.favorite.viewmodel.FavoriteTheaterState
import com.andriiginting.jetpackpro.presentation.favorite.viewmodel.FavoriteTheaterViewModelImpl
import com.andriiginting.jetpackpro.presentation.favorite.viewmodel.FavoriteViewModelFactory
import com.andriiginting.jetpackpro.presentation.module.InjectionModule
import com.andriiginting.jetpackpro.utils.makeGone
import com.andriiginting.jetpackpro.utils.makeVisible
import com.andriiginting.jetpackpro.utils.mapToMovie
import com.andriiginting.jetpackpro.utils.setGridView
import kotlinx.android.synthetic.main.fragment_favorite_theater.*

class FavoriteTheaterFragment : Fragment() {

    companion object {
        private const val GRID_COLUMN = 2
        fun newInstances(): FavoriteTheaterFragment = FavoriteTheaterFragment()
    }

    private lateinit var viewModel: FavoriteTheaterViewModelImpl
    private lateinit var pagingAdapter: TheaterPagingAdapter
    private val clickActionListener: ActionListener = this::navigateTo

    private var favoriteList: ArrayList<TheaterFavorite>? = arrayListOf()

    private val usecase by lazy {
        InjectionModule.provideFavoriteUseCase(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_theater, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders
            .of(this, FavoriteViewModelFactory(usecase))
            .get(FavoriteTheaterViewModelImpl::class.java)

        initData()
        initObserver()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupData()
    }

    private fun initData() {
        viewModel.getFavoriteTheater()
    }

    private fun setupData() {
        rvFavoriteFragment.apply {
            setGridView(GRID_COLUMN)
            adapter = pagingAdapter
        }
    }

    private fun navigateTo(theater: TheaterFavorite) {
        startActivity(
            DetailScreenActivity.navigate(requireActivity())
                .apply {
                    putExtra(MOVIE_KEY, theater.mapToMovie())
                    putExtra(SCREEN_TYPE, MOVIE_TYPE)
                }
        )
    }

    private fun setupAdapter() {
        pagingAdapter = TheaterPagingAdapter(clickActionListener)
    }

    private fun loadData(items: PagedList<TheaterFavorite>) {
        pagingAdapter.submitList(items)
    }

    private fun mapTheaterFavorite(items: List<TheaterFavorite>) {
        items.forEach(::safeAdd)
        Log.d("data-fav", favoriteList.toString())
    }

    private fun safeAdd(item: TheaterFavorite) {
        favoriteList?.add(item)
    }

    private fun loadEmptyScreen() {
        rvFavoriteFragment.makeGone()
        pbMainFavorite.makeGone()
        layoutEmpty.makeVisible()
    }

    private fun loadErrorScreen() {
        rvFavoriteFragment.makeGone()
        pbMainFavorite.makeGone()
        layoutError.makeVisible()
    }

    private fun initObserver() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is FavoriteTheaterState.HideLoading -> {
                    pbMainFavorite.makeGone()
                }
                is FavoriteTheaterState.ShowLoading -> {
                    pbMainFavorite.makeVisible()
                }
                is FavoriteTheaterState.LoadScreenError -> {
                    loadErrorScreen()
                }
                is FavoriteTheaterState.LoadFavoriteTheater -> {
                    pbMainFavorite.makeGone()
                    mapTheaterFavorite(state.data)
                    loadData(state.data)
                }
                is FavoriteTheaterState.LoadEmptyScreen -> {
                    loadEmptyScreen()
                }
            }
        })
    }
}
