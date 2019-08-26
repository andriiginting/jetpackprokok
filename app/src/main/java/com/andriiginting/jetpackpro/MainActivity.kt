package com.andriiginting.jetpackpro

import com.andriiginting.jetpackpro.base.BaseActivity
import com.andriiginting.jetpackpro.presentation.DicodingPager
import com.andriiginting.jetpackpro.presentation.favorite.FavoriteTheaterFragment
import com.andriiginting.jetpackpro.presentation.movie.MovieFragment
import com.andriiginting.jetpackpro.presentation.tv.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setupView() = setupPager()

    private fun setupPager(){
        val pagerAdapter = DicodingPager(supportFragmentManager)
        pagerAdapter.addFragment(MovieFragment.newInstance(), getString(R.string.text_title_movie))
        pagerAdapter.addFragment(TvShowFragment.newInstance(), getString(R.string.text_title_tvshow))
        pagerAdapter.addFragment(FavoriteTheaterFragment.newInstances(), getString(R.string.text_title_favorite))
        mainPager.adapter = pagerAdapter
        tabs.setupWithViewPager(mainPager)
    }
}
