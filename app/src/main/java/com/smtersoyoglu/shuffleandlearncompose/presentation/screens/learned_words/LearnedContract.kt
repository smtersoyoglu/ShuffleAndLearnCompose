package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem

object LearnedContract {
    data class UiState(
        val isLoading: Boolean = true,
        val learnedWords: List<WordItem> = emptyList(),
        val error: String? = null
    )

    sealed class UiAction {
        data class OnWordClicked(val wordId:Int) : UiAction()
    }

    sealed class UiEffect {
        data class NavigateToDetail(val wordId: Int) : UiEffect()
    }
}