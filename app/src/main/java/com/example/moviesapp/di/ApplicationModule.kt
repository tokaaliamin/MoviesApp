package com.example.moviesapp.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.example.moviesapp.data.MoviesListRemoteMediatorImpl
import com.example.moviesapp.data.local.dataSources.MoviesListLocalDataSource
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.data.remote.RetrofitClient
import com.example.moviesapp.data.remote.dataSources.MovieDetailsRemoteDataSource
import com.example.moviesapp.data.remote.dataSources.MovieDetailsRemoteDataSourceImpl
import com.example.moviesapp.data.remote.services.MovieDetailsService
import com.example.moviesapp.data.remote.services.MoviesListService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ApplicationModule {

    @Binds
    abstract fun bindMovieDetailsRemoteDataSource(dataSource: MovieDetailsRemoteDataSourceImpl): MovieDetailsRemoteDataSource

    @OptIn(ExperimentalPagingApi::class)
    @Binds
    abstract fun bindMovieListRemoteMediator(moviesListRemoteMediator: MoviesListRemoteMediatorImpl): RemoteMediator<Int, Movie>


    companion object {
        @Provides
        fun provideMoviesListLocalDataSource() = MoviesListLocalDataSource()

        @Provides
        fun provideMoviesListService() =
            RetrofitClient.retrofitClient.create(MoviesListService::class.java)

        @Provides
        fun provideMovieDetailsService() =
            RetrofitClient.retrofitClient.create(MovieDetailsService::class.java)
    }

}