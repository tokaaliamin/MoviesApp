package com.example.moviesapp.data.repos

import com.example.moviesapp.data.local.dataSources.MoviesListLocalDataSource
import com.example.moviesapp.data.local.models.toDomainMovie
import com.example.moviesapp.data.remote.dataSources.MoviesListRemoteDataSource
import com.example.moviesapp.data.remote.models.toDomainMovie
import com.example.moviesapp.data.remote.models.toLocalMovie
import com.example.moviesapp.domain.models.Movie
import com.example.moviesapp.utils.MyApplication
import com.example.moviesapp.utils.NetworkConnectivityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class MoviesListRepo() {
    private val moviesListRemoteDataSource by lazy { MoviesListRemoteDataSource() }
    private val moviesListLocalDataSource by lazy { MoviesListLocalDataSource() }
    private val networkConnectivityManager by lazy { NetworkConnectivityManager() }

    suspend fun discoverMovies(pageNumber: Int): Flow<Result<List<Movie>>> {
        return flow {
            try {
                //fetch local movies
                val localResult = moviesListLocalDataSource.discoverMovies()?.map { localMovie ->
                    localMovie.toDomainMovie()
                }
                localResult?.let { result -> emit(Result.success(result)) }
                //TODO remove the delay
                delay(3000)

                //check there is internet
                if (networkConnectivityManager.isOnline(MyApplication.appContext)) {
                    //fetch latest remote data
                    val result = moviesListRemoteDataSource.discoverMovies(pageNumber)
                    if (result.movies.isEmpty().not()) {
                        //update database with the new remote data
                        moviesListLocalDataSource.cacheMovies(result.movies.map { it.toLocalMovie() })
                    }
                    emit(Result.success(result.movies.map { remoteMovie ->
                        remoteMovie.toDomainMovie()
                    }))
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    suspend fun searchMovies(keyword: String, pageNumber: Int): Result<List<Movie>> {
        return withContext(Dispatchers.IO) {
            try {
                //search in remote data
                if (networkConnectivityManager.isOnline(MyApplication.appContext)) {
                    val result = moviesListRemoteDataSource.searchMovies(keyword, pageNumber)

                    Result.success(result.movies.map { remoteMovie ->
                        remoteMovie.toDomainMovie()
                    })
                } else {
                    //search in local DB
                    val localResult = moviesListLocalDataSource.searchMovies(keyword, pageNumber)
                        ?.map { localMovie ->
                            localMovie.toDomainMovie()
                        }
                    if (localResult == null) {
                        Result.success(emptyList())
                    } else {
                        Result.success(localResult)
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


}