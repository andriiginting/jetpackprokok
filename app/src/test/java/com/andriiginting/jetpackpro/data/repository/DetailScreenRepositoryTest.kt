package com.andriiginting.jetpackpro.data.repository

import com.andriiginting.jetpackpro.data.database.TheaterDAO
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.network.DicodingService
import com.andriiginting.jetpackpro.helper.TrampolineSchedulerRX
import com.andriiginting.jetpackpro.helper.load
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailScreenRepositoryTest {
    private val service = mock<DicodingService>()
    private val db = mock<TheaterDAO>()
    private val key = "key"
    private val id = "id"
    private var repository = DetailScreenRepository(service, db)
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

    @Test
    fun `given detail repository when get check isFavorite theater should return true`() {
        val favorite = TheaterFavorite(
            theaterFavoriteId = "31917",
            posterPath = "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
            theaterTitle = "Pretty Little Liars",
            overview = "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
            backdropPath = "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
            releaseDate = "2012-10-10"
        )
        val ids = 31917

        Mockito.`when`(db.isFavorite(ids))
            .thenReturn(Single.just(favorite))

        val test = db.isFavorite(ids).test()
        repository.isFavoriteTheater("$ids")

        test.apply {
            assertNoErrors()
            assertComplete()
            assertTerminated()
            assert(true)
        }

        verify(db, atLeastOnce()).isFavorite(ids)
    }

    @After
    fun tear() {
        TrampolineSchedulerRX.tearDown()
    }
}