package com.andriiginting.jetpackpro.base

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupView()
    }

    abstract fun getLayoutId(): Int

    abstract fun setupView()

    protected fun setupToolbarWithTitle(toolbar: Toolbar, title: String) {
        setupToolbar(toolbar).apply {
            toolbar.title = title
        }
    }

    private fun setupToolbar(toolbar: Toolbar): ActionBar? {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowHomeEnabled(true)
        return ab
    }
}