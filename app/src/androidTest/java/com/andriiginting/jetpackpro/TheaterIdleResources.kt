package com.andriiginting.jetpackpro

import androidx.test.espresso.IdlingResource
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity
import com.andriiginting.jetpackpro.utils.IdleResources.DECREMENT_IDLE_RESOURCES
import com.andriiginting.jetpackpro.utils.IdleResources.idleResources

class TheaterIdleResources : IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = TheaterIdleResources::class.java.simpleName

    override fun isIdleNow(): Boolean {
        val idle = (idleResources == DECREMENT_IDLE_RESOURCES) || (DetailScreenActivity.detailIdle == DECREMENT_IDLE_RESOURCES)

        if (idle) resourceCallback?.onTransitionToIdle()
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }
}