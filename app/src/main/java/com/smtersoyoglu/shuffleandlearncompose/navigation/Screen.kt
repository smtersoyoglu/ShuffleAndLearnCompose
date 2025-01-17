package com.smtersoyoglu.shuffleandlearncompose.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object WordMainScreen : Screens

    @Serializable
    data object LearnedWordsScreen : Screens

    @Serializable
    data object WordGameScreen : Screens

    @Serializable
    data class WordDetailScreen(val wordId: Int) : Screens

    @Serializable
    data object ChatPage : Screens

    @Serializable
    data object SplashScreen : Screens

    companion object {
        fun getRoute(screen: Screens): String {
            return screen::class.qualifiedName.orEmpty()
        }

        fun shouldShowBottomBar(currentRoute: String?): Boolean {
            return when (currentRoute) {
                getRoute(WordMainScreen), getRoute(LearnedWordsScreen), getRoute(WordGameScreen), getRoute(
                    ChatPage
                ) -> true

                else -> false
            }
        }
    }
}