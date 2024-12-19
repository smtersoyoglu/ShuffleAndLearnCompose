package com.smtersoyoglu.shuffleandlearncompose.screens.word_game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.AnswerInputField
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.ScoreDisplay
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.SubmitButton
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.TimerDisplay
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.WordGameCard

@Composable
fun WordGameScreen(navController: NavController,
                   viewModel: WordGameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Word Game ekranı kodları olucak
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Word Game Screen", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.padding(16.dp))
        // Timer ekleme
        TimerDisplay(timeRemaining = uiState.timer)
        Spacer(modifier = Modifier.padding(8.dp))
        // Score ekleme
        ScoreDisplay(correctCount = uiState.correctCount, incorrectCount = uiState.incorrectCount)

        Spacer(modifier = Modifier.padding(32.dp))
        // Kelime Kartı
        uiState.currentWord?.let { wordItem ->
            WordGameCard(wordItem = wordItem)
        } ?: run {
            // Eğer currentWord null ise bir loading veya error mesajı gösterilebilir.
            Text("Kelime Yükleniyor...", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.padding(16.dp))
        AnswerInputField()

        Spacer(modifier = Modifier.padding(16.dp))
        SubmitButton(onClick = { /* Kontrol et butonuna tıklandığında yapılacak işlemler */ })

    }
}

@Preview
@Composable
fun WordGameScreenPreview() {
    WordGameScreen(navController = NavController(LocalContext.current))
}