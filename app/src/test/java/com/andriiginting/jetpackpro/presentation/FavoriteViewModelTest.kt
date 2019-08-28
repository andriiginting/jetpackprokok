package com.andriiginting.jetpackpro.presentation

import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.data.repository.FavoriteRepository
import com.andriiginting.jetpackpro.domain.TheaterUseCase
import com.andriiginting.jetpackpro.domain.TheaterUseCaseMapper
import com.andriiginting.jetpackpro.helper.InstantRuleExecution
import com.andriiginting.jetpackpro.helper.TrampolineSchedulerRX
import com.andriiginting.jetpackpro.helper.mockPagedList
import com.andriiginting.jetpackpro.presentation.favorite.viewmodel.FavoriteTheaterState
import com.andriiginting.jetpackpro.presentation.favorite.viewmodel.FavoriteTheaterViewModelImpl
import com.nhaarman.mockito_kotlin.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class FavoriteViewModelTest {
    private val useCase = mock<TheaterUseCaseMapper>()
    private val observer = mock<Observer<FavoriteTheaterState>>()
    private val viewModel = FavoriteTheaterViewModelImpl(useCase)

    private val theater = TheaterFavorite(
        theaterFavoriteId = "id",
        posterPath = "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
        theaterTitle = "Pretty Little Liars",
        overview = "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
        backdropPath = "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
        releaseDate = "2012-10-10"
    )


    @Before
    fun setup() {
        MockitoAnnotations()
        InstantRuleExecution.start()
        TrampolineSchedulerRX.start()

        viewModel.state.observeForever(observer)
    }

    @Test
    fun `given fav viewmodel when get all data should return success`() {

        val pagedList = mockPagedList(listOf(theater))
        whenever(useCase.getAllTheaterFavorite()).thenReturn(Flowable.fromCallable { pagedList })

        viewModel.getFavoriteTheater()

        verify(observer, atLeastOnce()).onChanged(FavoriteTheaterState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(FavoriteTheaterState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(FavoriteTheaterState.LoadFavoriteTheater(pagedList))

        verify(useCase, atLeastOnce()).getAllTheaterFavorite()
    }

    @After
    fun tear() {
        InstantRuleExecution.tearDown()
        TrampolineSchedulerRX.tearDown()

        verifyNoMoreInteractions(useCase)
        clearInvocations(useCase)
    }


}