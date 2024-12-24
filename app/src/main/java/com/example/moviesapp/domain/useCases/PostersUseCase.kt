package com.example.moviesapp.domain.useCases

class PostersUseCase {

    operator fun invoke(imageSuffix: String?): String {
        return if (imageSuffix.isNullOrBlank())
            ""
        else
            "https://image.tmdb.org/t/p/w500/${imageSuffix}"
    }
}