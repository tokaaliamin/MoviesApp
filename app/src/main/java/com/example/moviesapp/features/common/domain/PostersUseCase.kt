package com.example.moviesapp.features.common.domain

class PostersUseCase {

    operator fun invoke(imageSuffix: String?): String {
        return if (imageSuffix.isNullOrBlank())
            ""
        else
            "https://image.tmdb.org/t/p/w500/${imageSuffix}"
    }
}