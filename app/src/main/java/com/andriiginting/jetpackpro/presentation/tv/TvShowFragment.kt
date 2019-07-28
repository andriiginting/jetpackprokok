package com.andriiginting.jetpackpro.presentation.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andriiginting.jetpackpro.R

class TvShowFragment : Fragment() {
    companion object {
        fun newInstance(): TvShowFragment = TvShowFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }
}
