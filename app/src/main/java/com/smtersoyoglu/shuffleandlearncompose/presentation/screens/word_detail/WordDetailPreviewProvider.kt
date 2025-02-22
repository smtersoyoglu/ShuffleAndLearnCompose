package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.DetailContract.UiState

class WordDetailPreviewProvider : PreviewParameterProvider<UiState> {
    override val values: Sequence<UiState>
        get() = sequenceOf(
            UiState(
                isLoading = false,
                word = WordItem(
                    id = 2,
                    translation = "Köpek",
                    english = "Dog",
                    url = "https://cdn.pixabay.com/photo/2020/02/19/12/25/pug-4862083_1280.png",
                    sentence = "The dog is playing with a ball in the garden.",
                    isLearned = false
                ),
                error = null
            ),
            UiState(
                isLoading = true,
                error = null
            )
        )
}