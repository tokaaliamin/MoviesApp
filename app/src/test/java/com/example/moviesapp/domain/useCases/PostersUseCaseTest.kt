package com.example.moviesapp.domain.useCases

import org.junit.Assert.assertEquals
import org.junit.Test

class PostersUseCaseTest {

    @Test
    fun invokePosterUseCase_getPosterUrl_null() {
        assertEquals("", PostersUseCase().invoke(null))
    }

    @Test
    fun invokePosterUseCase_getPosterUrl_blank() {
        assertEquals("", PostersUseCase().invoke(null))
    }

    @Test
    fun invokePosterUseCase_getPosterUrl_valid() {
        val imageSuffix = "12345"
        val result = PostersUseCase().invoke(imageSuffix)
        assertEquals("https://image.tmdb.org/t/p/w500/$imageSuffix", result)
    }
}