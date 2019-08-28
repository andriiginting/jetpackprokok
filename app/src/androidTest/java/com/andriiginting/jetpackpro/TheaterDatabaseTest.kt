package com.andriiginting.jetpackpro

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.andriiginting.jetpackpro.data.database.TheaterDatabase
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class TheaterDatabaseTest {

    private lateinit var database: TheaterDatabase

    @Before
    fun setupDB() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            TheaterDatabase::class.java
        ).build()
    }

    @Test
    fun insertTheaterDataToDatabase() {
        val theater = TheaterFavorite(
            theaterFavoriteId = "31917",
            posterPath = "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
            theaterTitle = "Pretty Little Liars",
            overview = "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
            backdropPath = "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
            releaseDate = "2012-10-10"
        )

        val insertData = database.theaterDAO().insertFavoriteTheater(theater).test()

        insertData.apply {
            assertNoErrors()
            assertComplete()
        }
    }

    @Test
    fun deleteTheaterDataToDatabase() {
        val theater = TheaterFavorite(
            theaterFavoriteId = "31917",
            posterPath = "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
            theaterTitle = "Pretty Little Liars",
            overview = "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
            backdropPath = "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
            releaseDate = "2012-10-10"
        )

        database.theaterDAO().insertFavoriteTheater(theater).test()
        val delete = database.theaterDAO().deleteTheater("31917").test()

        delete.apply {
            assertNoErrors()
            assertComplete()
        }
    }

    @After
    fun tearDB() = database.close()

}