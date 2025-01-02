package com.example.moviesapp.data.remote.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.data.remote.RetrofitClient
import com.example.moviesapp.data.remote.services.MoviesListService
import retrofit2.HttpException
import java.io.IOException

class SearchRemotePagingSource(
    val keyword: String
) : PagingSource<Int, Movie>() {
    private val moviesListService by lazy {
        RetrofitClient.getRetrofitClient().create(MoviesListService::class.java)
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = moviesListService.searchMovies(keyword, nextPageNumber)
            return LoadResult.Page(
                data = response.movies,
                prevKey = null, // Only paging forward.
                nextKey = response.page?.plus(1)
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}