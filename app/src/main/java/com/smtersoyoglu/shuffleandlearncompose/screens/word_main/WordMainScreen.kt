package com.smtersoyoglu.shuffleandlearncompose.screens.word_main

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smtersoyoglu.shuffleandlearncompose.navigation.Screen
import com.smtersoyoglu.shuffleandlearncompose.screens.word_main.components.WordCard
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.BackgroundColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaBold
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.HeaderColor

@Composable
fun WordMainScreen(
    viewModel: WordViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp)
            .background(BackgroundColor),
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

        when{
            uiState.isLoading -> CircularProgressIndicator()
            uiState.error != null -> Text(uiState.error!!)
            uiState.words.isEmpty() -> Text("No words available")
            else -> LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(uiState.words) { word ->
                    WordCard(wordItem = word) {
                        navController.navigate(Screen.WordDetailScreen.createRoute(word.id))
                    }
                }
            }
        }
    }
}
