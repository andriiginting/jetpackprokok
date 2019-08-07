package com.andriiginting.jetpackpro.presentation

import androidx.lifecycle.Observer
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.repository.HomeRepositoryContract
import com.andriiginting.jetpackpro.helper.InstantRuleExecution
import com.andriiginting.jetpackpro.helper.TrampolineSchedulerRX
import com.andriiginting.jetpackpro.presentation.movie.viewmodel.MovieState
import com.andriiginting.jetpackpro.presentation.movie.viewmodel.MovieViewModel
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.clearInvocations
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MovieViewModelTest {
    private var repo = mock<HomeRepositoryContract>()
    private var viewModel = MovieViewModel(repo)
    private var observer = mock<Observer<MovieState>>()

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
    fun `given movies viewModel when get movie should return success`() {
        `when`(repo.getPopularMovies())
            .thenReturn(Single.just(response))

        val res = repo.getPopularMovies().test()
        viewModel.getMovies()

        verify(repo, atLeastOnce()).getPopularMovies()
        verify(observer, atLeastOnce()).onChanged(MovieState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(MovieState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(MovieState.LoadMovieSuccess(response))

        res.apply {
            assertNoErrors()
            assertTerminated()
            assertComplete()
            assertValue{
                it == response
            }
        }
        verifyNoMoreInteractions(observer, repo)
        clearInvocations(observer, repo)
    }

    @Test
    fun `given given movies viewmodel when get movie and got error response`(){
        `when`(repo.getPopularMovies())
            .thenReturn(Single.error(Throwable("error msg")))

        viewModel.getMovies()

        verify(repo, atLeastOnce()).getPopularMovies()
        verify(observer, atLeastOnce()).onChanged(MovieState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(MovieState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(MovieState.LoadMovieError)

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