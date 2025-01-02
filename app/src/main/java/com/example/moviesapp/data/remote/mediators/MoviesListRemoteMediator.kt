package com.example.moviesapp.data.remote.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.moviesapp.data.local.dataSources.MoviesListLocalDataSource
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.data.remote.services.MoviesListService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesListRemoteMediatorImpl @Inject constructor(
    private val moviesListLocalDataSource: MoviesListLocalDataSource,
    private val moviesListService: MoviesListService
) : RemoteMediator<Int, Movie>() {
    private var currentPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): MediatorResult {
        return try {
            // The network load method takes an optional String
            // parameter. For every page after the first, pass the String
            // token returned from the previous page to let it continue
            // from where it left off. For REFRESH, pass null to load the
            // first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                // Get the last User object id for the next RemoteKey.
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    currentPage + 1
                }
            }


            val response = moviesListService.discoverMovies(loadKey)

            currentPage = response.page ?: 1

            // Store loaded data, and next key in transaction, so that
            // they're always consistent.
            moviesListLocalDataSource.updateMoviesData(
                loadType == LoadType.REFRESH,
                response.movies
            )

            // End of pagination has been reached if no users are returned from the service
            MediatorResult.Success(
                endOfPaginationReached = response.movies.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}