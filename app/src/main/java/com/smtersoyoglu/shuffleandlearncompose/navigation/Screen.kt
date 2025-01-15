package com.smtersoyoglu.shuffleandlearncompose.navigation

sealed class Screen(val route: String) {
    data object WordMainScreen : Screen(route = "word_main_screen")
    data object LearnedWordsScreen : Screen(route = "learnedWordsScreen")
    data object WordGameScreen : Screen(route = "wordGameScreen")
    data object WordDetailScreen : Screen(route = "word_detail_screen/{wordId}") {
        fun createRoute(wordId: Int) = "word_detail_screen/$wordId"
    }

    data object ChatPage : Screen(route = "chatPage")

    data object SplashScreen : Screen(route = "splash_screen")
}