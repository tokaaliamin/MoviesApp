package com.example.moviesapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import androidx.room.Room
import com.example.moviesapp.data.local.dataSources.MoviesListLocalDataSource
import com.example.moviesapp.data.local.database.AppDatabase
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.data.remote.RetrofitClient
import com.example.moviesapp.data.remote.dataSources.MovieDetailsDataSource
import com.example.moviesapp.data.remote.dataSources.MovieDetailsRemoteDataSource
import com.example.moviesapp.data.remote.mediators.MoviesListRemoteMediatorImpl
import com.example.moviesapp.data.remote.services.MovieDetailsService
import com.example.moviesapp.data.remote.services.MoviesListService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class ApplicationModule {

    @Binds
    abstract fun bindMovieDetailsRemoteDataSource(dataSource: MovieDetailsRemoteDataSource): MovieDetailsDataSource

    @OptIn(ExperimentalPagingApi::class)
    @Binds
    abstract fun bindMovieListRemoteMediator(moviesListRemoteMediator: MoviesListRemoteMediatorImpl): RemoteMediator<Int, Movie>


    companion object {
        @Provides
        fun provideMoviesListLocalDataSource(db: AppDatabase) = MoviesListLocalDataSource(db)

        @Provides
        fun provideMoviesListService() =
            RetrofitClient.retrofitClient.create(MoviesListService::class.java)

        @Provides
        fun provideMovieDetailsService() =
            RetrofitClient.retrofitClient.create(MovieDetailsService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object SingleModule {
    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, AppDatabase::class.java, "moviesDB")
        .build()
}