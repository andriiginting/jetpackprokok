package com.andriiginting.jetpackpro.presentation

import androidx.lifecycle.Observer
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.repository.DetailScreenRepositoryContract
import com.andriiginting.jetpackpro.helper.InstantRuleExecution
import com.andriiginting.jetpackpro.helper.TrampolineSchedulerRX
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenState
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.TheaterDetailViewModel
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.clearInvocations
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailScreenViewModelTest {
    private var repo = mock<DetailScreenRepositoryContract>()
    private var viewModel = TheaterDetailViewModel(repo)
    private var observer = mock<Observer<DetailScreenState>>()
    private val id = "id"

    private var response = MovieResponse(
        mutableListOf(
            MovieItem(
                id = "297761",
                movieId = "",
                posterPath = "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
                title = "Suicide Squad",
                overview = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
                releaseDate = "2016-08-03"
            ),
            MovieItem(
                id = "324668",
                movieId = "",
                posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
                title = "Jason Bourne",
                overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
                backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                releaseDate = "2016-07-27"
            )
        )
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        InstantRuleExecution.start()
        TrampolineSchedulerRX.start()

        viewModel.state.observeForever(observer)
    }

    @Test
    fun `given detail viewModel when get similar movie should return success`() {
        Mockito.`when`(repo.getSimilarMovie(id))
            .thenReturn(Single.just(response))

        val res = repo.getSimilarMovie(id).test()
        viewModel.getSimilarMovie(id)

        Mockito.verify(repo, atLeastOnce()).getSimilarMovie(id)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.ShowLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.HideLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.LoadSimilarMovieSuccess(response))

        res.apply {
            assertNoErrors()
            assertTerminated()
            assertComplete()
            assertValue {
                it == response
            }
        }
        verifyNoMoreInteractions(observer, repo)
        clearInvocations(observer, repo)
    }

    @Test
    fun `given given detail viewmodel when get movie and got error response`() {
        Mockito.`when`(repo.getSimilarMovie(id))
            .thenReturn(Single.error(Throwable("error msg")))

        viewModel.getSimilarMovie(id)

        Mockito.verify(repo, atLeastOnce()).getSimilarMovie(id)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.ShowLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.HideLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.LoadScreenError)

        verifyNoMoreInteractions(observer, repo)
        clearInvocations(observer, repo)
    }

    @Test
    fun `given detail viewModel when get similar tv show should return success`() {
        Mockito.`when`(repo.getSimilarTvShow(id))
            .thenReturn(Single.just(response))

        val res = repo.getSimilarTvShow(id).test()
        viewModel.getSimilarTv(id)

        Mockito.verify(repo, atLeastOnce()).getSimilarTvShow(id)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.ShowLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.HideLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.LoadSimilarTVSuccess(response))

        res.apply {
            assertNoErrors()
            assertTerminated()
            assertComplete()
            assertValue {
                it == response
            }
        }
        verifyNoMoreInteractions(observer, repo)
        clearInvocations(observer, repo)
    }

    @Test
    fun `given given detail viewmodel when get similar tv show and got error response`() {
        Mockito.`when`(repo.getSimilarTvShow(id))
            .thenReturn(Single.error(Throwable("error msg")))

        viewModel.getSimilarTv(id)

        Mockito.verify(repo, atLeastOnce()).getSimilarTvShow(id)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.ShowLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.HideLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.LoadScreenError)

        verifyNoMoreInteractions(observer, repo)
        clearInvocations(observer, repo)
    }

    @Test
    fun `given detail viewmodel when check favorite theater should return true`() {
        val theater = TheaterFavorite(
            theaterFavoriteId = "id",
            posterPath = "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
            theaterTitle = "Pretty Little Liars",
            overview = "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
            backdropPath = "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
            releaseDate = "2012-10-10"
        )

        Mockito.`when`(repo.isFavoriteTheater(id))
            .thenReturn(Single.just(theater))

        viewModel.isFavoriteTheater(id)

        Mockito.verify(repo, atLeastOnce()).isFavoriteTheater(id)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.IsFavoriteTheater(true))

        verifyNoMoreInteractions(observer, repo)
        clearInvocations(observer, repo)
    }

    @Test
    fun `given detail viewmodel when check favorite theater should return false`() {
        Mockito.`when`(repo.isFavoriteTheater(id))
            .thenReturn(Single.error(Throwable("error")))

        viewModel.isFavoriteTheater(id)

        Mockito.verify(repo, atLeastOnce()).isFavoriteTheater(id)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.IsFavoriteTheater(false))

        verifyNoMoreInteractions(observer, repo)
        clearInvocations(observer, repo)
    }

    @Test
    fun `given detail viewmodel when delete favorite theater should return success`() {
        Mockito.`when`(repo.deleteTheaterData(id))
            .thenReturn(Completable.complete())

        viewModel.deleteTheaterData(id)

        Mockito.verify(repo, atLeastOnce()).deleteTheaterData(id)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.SuccessDeleteTheater)

        verifyNoMoreInteractions(observer, repo)
        clearInvocations(observer, repo)
    }

    @Test
    fun `given detail viewmodel when delete favorite theater should return failed`() {
        Mockito.`when`(repo.deleteTheaterData(id))
            .thenReturn(Completable.error(Throwable("error")))

        viewModel.deleteTheaterData(id)

        Mockito.verify(repo, atLeastOnce()).deleteTheaterData(id)
        Mockito.verify(observer, atLeastOnce()).onChanged(DetailScreenState.FailedDeleteTheater)

        verifyNoMoreInteractions(observer, repo)
        clearInvocations(observer, repo)
    }

    @After
    fun tear() {
        InstantRuleExecution.tearDown()
        TrampolineSchedulerRX.tearDown()

        verifyNoMoreInteractions(repo)
        clearInvocations(repo)
    }
}