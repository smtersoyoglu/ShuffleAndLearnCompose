package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem

object GameContract {
    data class UiState(
        val wordList: List<WordItem>? = emptyList(),
        val currentWord: WordItem? = null,
        val timer: Int = 60,
        val correctCount: Int = 0,
        val incorrectCount: Int = 0,
        val isGameOver: Boolean = false,
        val isCorrect: Boolean? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
    )

    sealed class UiAction {
        data object StartGame : UiAction()
        data object ResetGame : UiAction()
        data class SubmitAnswer(val userAnswer: String) : UiAction()
        data object ExitGame : UiAction()
    }

    sealed class UiEffect {
        data object NavigateToMainScreen : UiEffect()
    }

}



