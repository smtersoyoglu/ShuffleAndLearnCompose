package com.smtersoyoglu.shuffleandlearncompose.screens.learned_words


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smtersoyoglu.shuffleandlearncompose.navigation.Screen
import com.smtersoyoglu.shuffleandlearncompose.screens.word_main.components.WordCard

@Composable
fun LearnedWordsScreen(
    navController: NavController,
    learnedWordsViewModel: LearnedWordsViewModel = hiltViewModel()
) {
    val learnedWordsState by learnedWordsViewModel.uiState.collectAsState()

    when {
        learnedWordsState.isLoading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }

        learnedWordsState.error != null -> {
            Text(
                text = learnedWordsState.error ?: "Unknown error",
                color = Color.Red,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        }

        learnedWordsState.learnedWords.isNotEmpty() -> {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(learnedWordsState.learnedWords) { word ->
                    WordCard(wordItem = word) {
                        navController.navigate(Screen.WordDetailScreen.createRoute(word.id, word.isLearned))
                    }
                }
            }
        }
        else -> {
            Text(
                text = "No learned words yet!",
                color = Color.Gray,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        }
    }
}