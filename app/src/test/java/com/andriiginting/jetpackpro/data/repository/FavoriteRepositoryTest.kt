package com.andriiginting.jetpackpro.data.repository

import com.andriiginting.jetpackpro.data.database.TheaterDAO
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.helper.TrampolineSchedulerRX
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class FavoriteRepositoryTest {
    private val database = mock<TheaterDAO>()
    private val repositoryImpl = FavoriteRepositoryImpl(database)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        TrampolineSchedulerRX.start()
    }

    private var response = listOf(
        TheaterFavorite(
            theaterFavoriteId = "31917",
            posterPath = "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
            theaterTitle = "Pretty Little Liars",
            overview = "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
            backdropPath = "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
            releaseDate = "2012-10-10"
        )
    )

    @Test
    fun `given fav repository when get all list theater should get empty`() {
        `when`(database.getAllFavoriteTheater())
            .thenReturn(Flowable.just(listOf()))

        val test = repositoryImpl.getFavoriteTheater().test()
        repositoryImpl.getFavoriteTheater()
        test.apply {
            assertNoErrors()
            assertTerminated()
            assertComplete()
        }
    }

    @Test
    fun `given fav repository when get all list theater should get response`() {
        `when`(database.getAllFavoriteTheater())
            .thenReturn(Flowable.just(response))

        val test = repositoryImpl.getFavoriteTheater().test()
        repositoryImpl.getFavoriteTheater()
        test.apply {
            assertNoErrors()
            assertTerminated()
            assertComplete()
            assertValue { actual ->
                actual == response
            }
        }
    }
}