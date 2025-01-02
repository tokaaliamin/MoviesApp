package com.example.moviesapp.data.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import androidx.paging.map
import com.example.moviesapp.data.local.dataSources.MoviesListLocalDataSource
import com.example.moviesapp.data.models.toDomainMovie
import com.example.moviesapp.data.remote.dataSources.SearchRemotePagingSource
import com.example.moviesapp.domain.models.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesListRepo @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val moviesListLocalDataSource: MoviesListLocalDataSource,
    private val searchRemotePagingSource: SearchRemotePagingSource,
    private val moviesListRemoteMediator: RemoteMediator<Int, com.example.moviesapp.data.models.Movie>
) {

    private val _searchKeyword = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getMovies(): Flow<PagingData<Movie>> {
        return _searchKeyword.flatMapLatest { keyword ->
            println(keyword)
            if (keyword.isBlank()) {
                getDiscoverMoviesPager()
            } else {
                getSearchMoviesPager(keyword)
            }
        }
    }

    private fun getSearchMoviesPager(keyword: String): Flow<PagingData<Movie>> {
        // Use PagingSource for search results
        return Pager(
            config = PagingConfig(pageSize = 20)
        ) {
            searchRemotePagingSource.apply { setSearchKeyword(keyword) }
        }.flow.map { pagingData ->
            pagingData.map { dataMovie -> dataMovie.toDomainMovie() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun getDiscoverMoviesPager(): Flow<PagingData<Movie>> {
        // Use RemoteMediator for regular movie list
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = moviesListRemoteMediator
        ) {
            moviesListLocalDataSource.discoverMovies()
        }.flow.map { pagingData ->
            pagingData.map { dataMovie -> dataMovie.toDomainMovie() }
        }
    }

    suspend fun updateSearchQuery(query: String) {
        _searchKeyword.emit(query)
    }

}