package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smtersoyoglu.shuffleandlearncompose.screens.word_detail.components.WordDetailContent

@Composable
fun WordDetailScreen(
    navController: NavController,
    wordId: Int,
    viewModel: WordDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Kelimeyi yükle
    LaunchedEffect(wordId) {
        viewModel.fetchWordById(wordId)
    }

    // Snackbar'ı göster
    LaunchedEffect(snackbarMessage) {
        if (snackbarMessage != null) {
            snackbarHostState.showSnackbar(snackbarMessage!!)
            viewModel.clearSnackbarMessage()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter) // Snackbar ekranın altında görünür
        )

        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            uiState.word != null -> {
                uiState.word?.let { word ->
                    WordDetailContent(
                        wordItem = word,
                        onLearned = { viewModel.toggleLearnedStatus(word) },
                        onBack = { navController.popBackStack() },
                        isLearned = word.isLearned
                    )
                }
            }
            uiState.error != null -> {
                Text(
                    text = uiState.error ?: "Bir hata oluştu",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
