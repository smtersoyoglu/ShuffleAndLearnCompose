package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.DetailContract.UiEffect
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.DetailContract.UiAction
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.DetailContract.UiState
import com.smtersoyoglu.shuffleandlearncompose.common.CollectWithLifecycle
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components.ColorBlock
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components.HeaderSection
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components.LearnButton
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components.TTSButton
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components.WordDetailsText
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components.WordImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun WordDetailScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    uiAction: (UiAction) -> Unit,
    uiEffect: Flow<UiEffect>,
    onNavigateBack: () -> Unit,
) {
    val context = LocalContext.current

    uiEffect.CollectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            is UiEffect.NavigateToBack -> {
                onNavigateBack()
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            uiState.word != null -> {
                uiState.word.let { word ->
                    WordDetailContent(
                        wordItem = word,
                        onLearned = { uiAction(UiAction.ToggleLearnedStatus(word)) },
                        onBack = onNavigateBack,
                        isLearned = word.isLearned
                    )
                }
            }

            uiState.error != null -> {
                Text(
                    text = uiState.error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun WordDetailContent(
    wordItem: WordItem,
    onLearned: () -> Unit,
    onBack: () -> Unit,
    isLearned: Boolean,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Sağ üstteki renk bloğu
        ColorBlock()

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HeaderSection(onBack = onBack)

            TTSButton(textToSpeak = wordItem.english)

            Spacer(modifier = Modifier.height(12.dp))

            WordImage(imageUrl = wordItem.url, description = wordItem.english)

            Spacer(modifier = Modifier.height(16.dp))

            WordDetailsText(
                translation = wordItem.translation,
                english = wordItem.english,
                sentence = wordItem.sentence ?: "No example sentence provided"
            )

            LearnButton(isLearned = isLearned, onLearned = onLearned)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordDetailScreenPreview(
    @PreviewParameter(WordDetailPreviewProvider::class) uiState: UiState,
) {
    WordDetailScreen(
        uiState = uiState,
        uiAction = {},
        uiEffect = emptyFlow(),
        onNavigateBack = {},
    )
}