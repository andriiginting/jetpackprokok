package com.andriiginting.jetpackpro.data.repository

import androidx.paging.DataSource
import com.andriiginting.jetpackpro.data.database.TheaterDAO
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.helper.TrampolineSchedulerRX
import com.andriiginting.jetpackpro.helper.mockPagedList
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class FavoriteRepositoryTest {
    private val database = mock<TheaterDAO>()
    private val dataSource = mock<DataSource.Factory<Int, TheaterFavorite>>()
    private val repositoryImpl = FavoriteRepositoryImpl(database)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        TrampolineSchedulerRX.start()
    }

    @Test
    fun `given fav repository when get all list theater should get empty`() {
        `when`(database.getAllFavoriteTheater())
            .thenReturn(dataSource)

        val test = repositoryImpl.getFavoriteTheater()
        repositoryImpl.getFavoriteTheater()
        Assert.assertNotNull(test)
    }
}