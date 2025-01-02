package com.example.moviesapp.utils

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.example.moviesapp.utils.ErrorParser.getLoadStateError
import com.example.moviesapp.utils.ErrorParser.isLoadStateError
import com.example.moviesapp.utils.ErrorParser.parseThrowable
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class ErrorParserTest {

    @Test
    fun getLoadStateError_loadState_errorMessage() {
        val loadState = CombinedLoadStates(
            refresh = LoadState.Error(Exception("Test error")),
            append = LoadState.NotLoading(endOfPaginationReached = false),
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            source = LoadStates(
                refresh = LoadState.Error(Exception("Test error")),
                append = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )
        val error = getLoadStateError(loadState)
        assertNotNull(error)
    }


    @Test
    fun isLoadStateError_loadState_doesExist() {
        val loadState = CombinedLoadStates(
            refresh = LoadState.Error(Exception("Test error")),
            append = LoadState.NotLoading(endOfPaginationReached = false),
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            source = LoadStates(
                refresh = LoadState.Error(Exception("Test error")),
                append = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )
        val isError = isLoadStateError(loadState)
        assertTrue(isError)
    }

    @Test
    fun isLoadStateError_loadState_doesntExist() {
        val loadState = CombinedLoadStates(
            refresh = LoadState.NotLoading(endOfPaginationReached = true),
            append = LoadState.NotLoading(endOfPaginationReached = true),
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            source = LoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true),
                prepend = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )
        val isError = isLoadStateError(loadState)
        assertFalse(isError)
    }


    @Test
    fun parseThrowable_noNetworkError_returnsStringWrapper() {
        val throwable = IOException("Internet connection error")
        val stringWrapper = parseThrowable(throwable)
        assertNotNull(stringWrapper.stringInt)
    }
}