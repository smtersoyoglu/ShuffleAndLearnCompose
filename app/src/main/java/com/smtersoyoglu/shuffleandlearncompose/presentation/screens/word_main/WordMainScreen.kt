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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smtersoyoglu.shuffleandlearncompose.navigation.Screen
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.components.WordCard
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaBold
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.HeaderColor
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordMainScreen(
    viewModel: WordViewModel = hiltViewModel(),
    navController: NavController,
) {
// ViewModel'deki uiState (StateFlow) değişikliklerini dinleyerek,
// Compose ekranında sürekli güncellenen bir state oluşturur.
// Bu sayede Flow'da bir değişiklik olduğunda UI otomatik olarak yeniden çizilir.
    val uiState by viewModel.uiState.collectAsState() // collectAsState(), yalnızca Flow veya LiveData gibi reaktif veri yapılarını dinlemek için kullanılır
    var shuffledWords by remember { mutableStateOf(uiState.words) }

    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(uiState.words) {
        // ViewModel'den gelen kelimeler değiştiğinde shuffledWords güncellenir
        shuffledWords = uiState.words
    }

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(pullToRefreshState.isRefreshing) {
            shuffledWords = uiState.words.shuffled()
            delay(500L) // 1 saniye bekleme
            pullToRefreshState.endRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
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
                uiState.error != null -> Text(uiState.error!!, color = Color.Red)
                else ->
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(shuffledWords) { word ->
                            WordCard(wordItem = word) {
                                navController.navigate(Screen.WordDetailScreen.createRoute(word.id))
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
