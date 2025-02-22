package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem

object WordContract {
    data class UiState(
        val isLoading: Boolean = true,
        val words: List<WordItem> = emptyList(),
        val shuffledWords: List<WordItem> = emptyList(),
        val error: String? = null,
    )

    sealed class UiAction {
        data class OnWordClicked(val wordId: Int) : UiAction()
        data object ShuffleWords : UiAction()
        data object ResetShuffle : UiAction()

    }

    sealed class UiEffect {
        data class NavigateToDetail(val wordId: Int) : UiEffect()
    }

}