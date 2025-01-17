package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.DetailContract.UiEffect
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.DetailContract.UiAction
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.DetailContract.UiState
import androidx.navigation.NavController
import com.smtersoyoglu.shuffleandlearncompose.common.CollectWithLifecycle
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components.WordDetailContent
import kotlinx.coroutines.flow.Flow

@Composable
fun WordDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    uiState: UiState,
    uiAction: (UiAction) -> Unit,
    uiEffect: Flow<UiEffect>,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    uiEffect.CollectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
            is UiEffect.NavigateToBack -> { onNavigateBack() }
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
                        onBack = onNavigateBack ,
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