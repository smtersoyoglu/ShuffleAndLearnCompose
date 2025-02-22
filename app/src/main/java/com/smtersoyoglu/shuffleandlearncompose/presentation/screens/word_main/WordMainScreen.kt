package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.WordContract.UiEffect
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.WordContract.UiAction
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.WordContract.UiState
import com.smtersoyoglu.shuffleandlearncompose.common.CollectWithLifecycle
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.components.WordCard
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaBold
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.HeaderColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordMainScreen(
    uiState: UiState,
    uiAction: (UiAction) -> Unit,
    uiEffect: Flow<UiEffect>,
    onNavigateDetail: (Int) -> Unit,
) {
    uiEffect.CollectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.NavigateToDetail -> onNavigateDetail(effect.wordId)
        }
    }

    // Her ekran görüntülendiğinde shuffle'ı resetleme
    DisposableEffect(Unit) {
        uiAction(UiAction.ResetShuffle)
        onDispose { }
    }


    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(pullToRefreshState.isRefreshing) {
            uiAction(UiAction.ShuffleWords)
            delay(500L)
            pullToRefreshState.endRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        val displayWords = uiState.shuffledWords.ifEmpty {
            uiState.words
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Word Cards",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = HeaderColor,
                    fontFamily = FredokaBold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.error != null -> Text(uiState.error, color = Color.Red)
                else ->
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(displayWords) { word ->
                            WordCard(wordItem = word) {
                                uiAction(UiAction.OnWordClicked(word.id))
                            }
                        }
                    }
            }
        }

        if (pullToRefreshState.isRefreshing) {
            PullToRefreshContainer(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp),
                state = pullToRefreshState,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordMainScreenPreview(
    @PreviewParameter(WordMainPreviewProvider::class) uiState: UiState,
) {
    WordMainScreen(
        uiState = uiState,
        uiAction = {},
        uiEffect = emptyFlow(),
        onNavigateDetail = {}
    )
}