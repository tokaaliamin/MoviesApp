package com.example.moviesapp.domain.useCases

import com.example.moviesapp.domain.models.ImageSize

class ImagesUseCase {

    operator fun invoke(imageSuffix: String?, imageSize: ImageSize = ImageSize.POSTER): String {

        var width = 0
        when {
            imageSuffix.isNullOrBlank() -> return ""
            imageSize == ImageSize.POSTER ->
                width = 200

            imageSize == ImageSize.BACKDROP ->
                width = 500
        }
        return "https://image.tmdb.org/t/p/w${width}/${imageSuffix}"
    }
}