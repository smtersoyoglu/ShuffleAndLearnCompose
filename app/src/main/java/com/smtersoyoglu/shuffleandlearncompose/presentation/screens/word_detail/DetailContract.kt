package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem

object DetailContract {
    // ekranda göstereceğim verileri tutucaksın + isLoading ve error
    data class UiState(
        val isLoading: Boolean = false,
        val word: WordItem? = null,
        val error: String? = null
    )

    // butona basıp işlem yaptırma (sayfa içinde) favori butonu learned verileri yükleme fonk, bunun gibi işlemler
    sealed class UiAction {
        data class ToggleLearnedStatus(val word: WordItem) : UiAction()
    }


    // navigation + toast alert vs gibi işlemleri yönetiğin yer
    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToBack : UiEffect()
    }
}