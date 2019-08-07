package com.andriiginting.jetpackpro.presentation

import androidx.lifecycle.Observer
import com.andriiginting.jetpackpro.data.model.TvItem
import com.andriiginting.jetpackpro.data.model.TvResponse
import com.andriiginting.jetpackpro.data.repository.HomeRepositoryContract
import com.andriiginting.jetpackpro.helper.InstantRuleExecution
import com.andriiginting.jetpackpro.helper.TrampolineSchedulerRX
import com.andriiginting.jetpackpro.presentation.tv.viewmodel.TvShowViewModel
import com.andriiginting.jetpackpro.presentation.tv.viewmodel.TvState
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.clearInvocations
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TvShowViewModelTest {
    private var repo = mock<HomeRepositoryContract>()
    private var viewModel = TvShowViewModel(repo)
    private var observer = mock<Observer<TvState>>()

    private var response = TvResponse(
        mutableListOf(
            TvItem(
                id = "31917",
                posterPath = "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
                title = "Pretty Little Liars",
                overview = "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
                backdropPath = "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
                releaseDate = "2012-10-10"
            ),
            TvItem(
                id = "324668",
                posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
                title = "Jason Bourne",
                overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
                backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                releaseDate = "2012-10-10"
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
    fun `given movies viewModel when get tv show should return success`() {
        Mockito.`when`(repo.getTvShowMovie())
            .thenReturn(Single.just(response))

        val res = repo.getTvShowMovie().test()
        viewModel.getTvShow()

        Mockito.verify(repo, atLeastOnce()).getTvShowMovie()
        Mockito.verify(observer, atLeastOnce()).onChanged(TvState.ShowLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(TvState.HideLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(TvState.LoadMovieSuccess(response))

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
    fun `given given movies viewmodel when get tv show and got error response`() {
        Mockito.`when`(repo.getTvShowMovie())
            .thenReturn(Single.error(Throwable("error msg")))

        viewModel.getTvShow()

        Mockito.verify(repo, atLeastOnce()).getTvShowMovie()
        Mockito.verify(observer, atLeastOnce()).onChanged(TvState.ShowLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(TvState.HideLoading)
        Mockito.verify(observer, atLeastOnce()).onChanged(TvState.LoadMovieError)

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