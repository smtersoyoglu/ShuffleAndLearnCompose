package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.LearnedContract.UiState
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.LearnedContract.UiEffect
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.LearnedContract.UiAction
import com.smtersoyoglu.shuffleandlearncompose.R
import com.smtersoyoglu.shuffleandlearncompose.common.CollectWithLifecycle
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.components.LottieEmptyState
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.components.WordCard
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.BackgroundColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun LearnedWordsScreen(
    uiState: UiState,
    uiAction: (UiAction) -> Unit,
    uiEffect: Flow<UiEffect>,
    onNavigateToLearnedWordDetail: (Int) -> Unit,

    ) {

    uiEffect.CollectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.NavigateToDetail -> onNavigateToLearnedWordDetail(effect.wordId)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            }

            uiState.error != null -> {
                Text(
                    text = uiState.error ?: "Unknown error",
                    color = Color.Red,
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }

            uiState.learnedWords.isNotEmpty() -> {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(uiState.learnedWords) { word ->
                        WordCard(wordItem = word) {
                            uiAction(UiAction.OnWordClicked(word.id))
                        }
                    }
                }
            }

            else -> {
                LottieEmptyState(
                    lottieResId = R.raw.anim_empty,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LearnedWordsScreenPreview(
    @PreviewParameter(LearnedWordsPreviewProvider::class) uiState: UiState,
) {
   LearnedWordsScreen(
       uiState = uiState,
       uiAction = {},
       uiEffect = emptyFlow(),
       onNavigateToLearnedWordDetail = {}
   )
}