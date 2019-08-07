package com.andriiginting.jetpackpro.data.repository

import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.network.DicodingService
import com.andriiginting.jetpackpro.helper.TrampolineSchedulerRX
import com.andriiginting.jetpackpro.helper.load
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailScreenRepositoryTest {
    private val service = mock<DicodingService>()
    private val key = "key"
    private val id = "id"
    private var repository = DetailScreenRepository(service)
    private var error = Throwable("error msg")

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
        TrampolineSchedulerRX.start()
    }

    @Test
    fun `given detail repository when get similar movie from API should return success`() {
        Mockito.`when`(service.getSimilarMovie(id, key))
            .thenReturn(Single.just(response))

        val test = service.getSimilarMovie(id, key).test()
        repository.getSimilarMovie(id)

        test.apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                it == load(MovieResponse::class.java, "movie_response.json")
            }
        }

        verify(service, atLeastOnce()).getSimilarMovie(id, key)
    }

    @Test
    fun `given detail repository when get similar movie from API should return error`() {
        Mockito.`when`(service.getSimilarMovie(id, key))
            .thenReturn(Single.error(error))

        val test = service.getSimilarMovie(id, key).test()
        repository.getSimilarMovie(id)

        test.apply {
            assertError(error)
        }

        verify(service, atLeastOnce()).getSimilarMovie(id, key)
    }

    @Test
    fun `given detail repository when get similar tv show from API should return success`() {
        Mockito.`when`(service.getSimilarTvShow(id, key))
            .thenReturn(Single.just(response))

        val test = service.getSimilarTvShow(id, key).test()
        repository.getSimilarTvShow(id)

        test.apply {
            assertComplete()
            awaitTerminalEvent()
            assertNoErrors()
        }

        verify(service, atLeastOnce()).getSimilarTvShow(id, key)
    }

    @Test
    fun `given detail repository when get similar tv show from API should return error`() {
        Mockito.`when`(service.getSimilarTvShow(id, key))
            .thenReturn(Single.error(error))

        val test = service.getSimilarTvShow(id, key).test()
        repository.getSimilarTvShow(id)

        test.apply {
            assertError(error)
        }

        verify(service, atLeastOnce()).getSimilarTvShow(id, key)
    }

    @After
    fun tear() {
        TrampolineSchedulerRX.tearDown()
    }
}