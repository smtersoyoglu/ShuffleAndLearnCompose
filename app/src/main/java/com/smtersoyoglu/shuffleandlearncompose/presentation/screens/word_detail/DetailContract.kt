package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem

object DetailContract {
    data class UiState(
        val isLoading: Boolean = false,
        val word: WordItem? = null,
        val error: String? = null
    )

    sealed class UiAction {
        data class ToggleLearnedStatus(val word: WordItem) : UiAction()
    }


    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToBack : UiEffect()
    }
}