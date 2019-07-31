package com.andriiginting.jetpackpro.presentation.TheaterDetailScreen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import com.andriiginting.jetpackpro.R
import com.andriiginting.jetpackpro.base.BaseActivity
import com.andriiginting.jetpackpro.data.model.MovieItem

class DetailScreenActivity : BaseActivity() {
    companion object {
        const val MOVIE_KEY = "movieKey"


        fun navigate(activity: Activity): Intent = Intent(activity, DetailScreenActivity::class.java)
    }

    override fun getLayoutId(): Int = R.layout.activity_detail_screen
    override fun setupView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
