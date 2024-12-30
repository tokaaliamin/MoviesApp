package com.example.moviesapp.utils

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.moviesapp.R
import com.example.moviesapp.data.remote.models.ErrorResponse
import com.example.moviesapp.ui.models.StringWrapper
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

object ErrorParser {
    fun getLoadStateError(loadState: CombinedLoadStates?): Throwable? {
        return (loadState?.append as? LoadState.Error)?.error
            ?: (loadState?.prepend as? LoadState.Error)?.error
            ?: (loadState?.refresh as? LoadState.Error)?.error
    }

    fun isLoadStateError(loadState: CombinedLoadStates?): Boolean {
        return loadState?.append is LoadState.Error || loadState?.prepend is LoadState.Error || loadState?.refresh is LoadState.Error
    }

    fun parseThrowable(throwable: Throwable): StringWrapper {
        return when (throwable) {
            is IOException ->
                StringWrapper(stringInt = R.string.please_check_your_internet_connection)

            is HttpException -> {
                val errorBody = throwable.response()?.errorBody()?.string()
                if (!errorBody.isNullOrEmpty()) {
                    try {
                        val errorResponse =
                            Json.decodeFromString<ErrorResponse>(errorBody)
                        StringWrapper(message = errorResponse.statusMessage)
                    } catch (e: Exception) {
                        StringWrapper(stringInt = R.string.failed_to_parse_error_response)
                    }
                } else {
                    StringWrapper(stringInt = R.string.failed_to_parse_error_response)
                }
            }

            else -> {
                StringWrapper(stringInt = R.string.failed_to_parse_error_response)
            }
        }
    }

}