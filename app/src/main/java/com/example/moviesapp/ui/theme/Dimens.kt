package com.example.moviesapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

class Dimens {

    val paddingMicro = 2.dp
    val paddingTiny = 4.dp
    val paddingSmall = 8.dp
    val paddingMedium = 16.dp
    val paddingLarge = 24.dp

    val roundedCornerMedium = 6.dp
    val roundedCornerXLarge = 16.dp

    val sizeRatingStar = 30.dp

    val heightCard = 250.dp
    val heightPoster = 200.dp
    val heightSearchBar = 56.dp
    val heightErrorPlaceholder = 200.dp
    val heightNoDataPlaceholder = 200.dp
    val heightBackdrop = 200.dp
    val heightBackdropOverlay = 50.dp
    val heightSpacerSmall = 8.dp
    val heightSpacerMedium = 16.dp
    val heightSpacerLarge = 24.dp


    val widthErrorPlaceholder = 300.dp
    val widthPoster = 150.dp

    val moviesGridColumnsCount = 2


    companion object {

        val MaterialTheme.dimens: Dimens
            @Composable
            @ReadOnlyComposable
            get() = LocalBaseDimensImpl.current

        internal val LocalBaseDimensImpl = staticCompositionLocalOf<Dimens> { Dimens() }
    }
}