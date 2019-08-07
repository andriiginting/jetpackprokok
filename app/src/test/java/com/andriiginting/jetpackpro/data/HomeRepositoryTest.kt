package com.andriiginting.jetpackpro.data

import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.model.TvItem
import com.andriiginting.jetpackpro.data.model.TvResponse
import com.andriiginting.jetpackpro.data.network.DicodingService
import com.andriiginting.jetpackpro.data.repository.HomeRepository
import com.andriiginting.jetpackpro.helper.TrampolineSchedulerRX
import com.andriiginting.jetpackpro.helper.load
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.*
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HomeRepositoryTest {
    private val service = mock<DicodingService>()
    private val key = "keys"
    private var repository = HomeRepository(service)
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

    private var tvResponse = TvResponse(
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
        TrampolineSchedulerRX.start()
    }

    @Test
    fun `given repository when get popular movie from API should return success`() {
        Mockito.`when`(service.getPopularMovies(key))
            .thenReturn(Single.just(response))

        val test = service.getPopularMovies(key).test()
        repository.getPopularMovies()

        test.apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                it == load(MovieResponse::class.java, "movie_response.json")
            }
        }

        verify(service, atLeastOnce()).getPopularMovies(key)
    }

    @Test
    fun `given repository when get popular movie from API should return error`() {
        Mockito.`when`(service.getPopularMovies(key))
            .thenReturn(Single.error(error))

        val test = service.getPopularMovies(key).test()
        repository.getPopularMovies()

        test.apply {
            assertError(error)
        }

        verify(service, atLeastOnce()).getPopularMovies(key)
    }

    @Test
    fun `given repository when get popular tv show from API should return success`() {
        Mockito.`when`(service.getPopularTvShow(key))
            .thenReturn(Single.just(tvResponse))

        val test = service.getPopularTvShow(key).test()
        repository.getTvShowMovie()

        test.apply {
            assertComplete()
            awaitTerminalEvent()
            assertNoErrors()
        }

        verify(service, atLeastOnce()).getPopularTvShow(key)
    }

    @Test
    fun `given repository when get popular tv show from API should return error`() {
        Mockito.`when`(service.getPopularTvShow(key))
            .thenReturn(Single.error(error))

        val test = service.getPopularTvShow(key).test()
        repository.getTvShowMovie()

        test.apply {
            assertError(error)
        }

        verify(service, atLeastOnce()).getPopularTvShow(key)
    }

    @After
    fun tear() {
        TrampolineSchedulerRX.tearDown()
    }
}