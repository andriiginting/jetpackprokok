package com.andriiginting.jetpackpro.presentation.movie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andriiginting.jetpackpro.R

class MovieFragment : Fragment() {
    companion object {
        fun newInstance(): MovieFragment = MovieFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

}
