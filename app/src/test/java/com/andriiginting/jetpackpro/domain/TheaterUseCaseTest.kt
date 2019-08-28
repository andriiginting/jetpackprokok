package com.andriiginting.jetpackpro.domain

import androidx.paging.DataSource
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.data.repository.FavoriteRepository
import com.andriiginting.jetpackpro.helper.InstantRuleExecution
import com.andriiginting.jetpackpro.helper.TrampolineSchedulerRX
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class TheaterUseCaseTest {

    private val repository = mock<FavoriteRepository>()
    private val useCase = TheaterUseCase(repository)

    private val dataFactory = mock<DataSource.Factory<Int, TheaterFavorite>>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        InstantRuleExecution.start()
        TrampolineSchedulerRX.start()
    }

    @Test
    fun `given favorite usecase when mapping data from db should return pagedlist`(){
        whenever(repository.getFavoriteTheater()).thenReturn(dataFactory)

        useCase.getAllTheaterFavorite()
        Assert.assertNotNull(useCase.getAllTheaterFavorite())
        verify(repository, atLeastOnce()).getFavoriteTheater()
    }

    @After
    fun tear() {
        TrampolineSchedulerRX.tearDown()
        InstantRuleExecution.tearDown()

        verifyNoMoreInteractions(repository)
        clearInvocations(repository)
    }
}