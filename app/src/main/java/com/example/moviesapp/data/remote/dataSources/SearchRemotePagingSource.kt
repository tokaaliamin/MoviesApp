package com.example.moviesapp.data.remote.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.data.remote.services.MoviesListService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRemotePagingSource @Inject constructor(
    private val moviesListService: MoviesListService
) : PagingSource<Int, Movie>() {

    private var keyword: String? = null

    fun setSearchKeyword(searchKeyword: String) {
        keyword = searchKeyword
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        if (keyword == null) {
            throw IllegalStateException("Search keyword must be set before loading data")
        }

        try {
            val nextPageNumber = params.key ?: 1
            val response = moviesListService.searchMovies(keyword!!, nextPageNumber)
            return LoadResult.Page(
                data = response.movies,
                prevKey = null, // Only paging forward.
                nextKey = response.page?.plus(1)
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}