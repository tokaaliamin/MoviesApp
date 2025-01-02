package com.example.moviesapp.data.repos

import com.example.moviesapp.data.dataSources.FakeMovieDetailsDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieDetailsRepoTest {

    @Test
    fun fetchMovieDetails_successful_data() {
        val dataSource = FakeMovieDetailsDataSource()
        val repo = MovieDetailsRepo(dataSource)
        runBlocking {
            val movieId = 1
            val result = repo.fetchMovieDetails(movieId)
            assertTrue(result.isSuccess)
            val response = result.getOrNull()
            assertNotNull(response)
            assertEquals(movieId, response?.id)
        }
    }

    @Test
    fun fetchMovieDetails_failed_notFound() {
        val dataSource = FakeMovieDetailsDataSource()
        val repo = MovieDetailsRepo(dataSource)
        runBlocking {
            val movieId = -1
            try {
                val result = repo.fetchMovieDetails(movieId)
                assertTrue(result.isFailure)
            } catch (t: Throwable) {
                assertEquals("Not found", t.message)
            }
        }
    }
}