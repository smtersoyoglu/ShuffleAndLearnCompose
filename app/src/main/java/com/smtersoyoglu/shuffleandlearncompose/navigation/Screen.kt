package com.smtersoyoglu.shuffleandlearncompose.navigation

sealed class Screen (val route: String) {
    object WordMainScreen : Screen("word_main_screen")
    object WordDetailScreen : Screen("word_detail_screen/{wordId}") {
        fun createRoute(wordId: Int) = "word_detail_screen/$wordId"
    }
}