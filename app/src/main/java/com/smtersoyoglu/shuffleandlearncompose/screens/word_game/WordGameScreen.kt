package com.smtersoyoglu.shuffleandlearncompose.screens.word_game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smtersoyoglu.shuffleandlearncompose.R
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.AnimatedResponse
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.AnswerInputField
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.GameOverDialog
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.ScoreDisplay
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.TimerDisplay
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components.WordGameCard

@Composable
fun WordGameScreen(
    navController: NavController,
    viewModel: WordGameViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()


    if (uiState.isGameOver) {
        GameOverDialog(
            correctCount = uiState.correctCount,
            incorrectCount = uiState.incorrectCount,
            onRetry = {
                viewModel.resetGame()
            },
            onExit = { navController.navigate("word_main_screen") })
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Timer ekleme
            TimerDisplay(timeRemaining = uiState.timer)
            Spacer(modifier = Modifier.padding(8.dp))
            // Score ekleme
            ScoreDisplay(
                correctCount = uiState.correctCount,
                incorrectCount = uiState.incorrectCount
            )

            Spacer(modifier = Modifier.padding(24.dp))
            // Kelime Kartı
            uiState.currentWord?.let { wordItem ->
                WordGameCard(wordItem = wordItem)
            } ?: run {
                // Eğer currentWord null ise bir loading veya error mesajı gösterilebilir.
                Text("Kelime Yükleniyor...", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.padding(16.dp))
            // Cevap Girdisi ve Kontrol Etme Butonu
            AnswerInputField(
                onAnswerSubmit = { userAnswer ->
                    viewModel.checkAnswer(userAnswer)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            // Animasyon
            AnimatedResponse(
                animationRes = when (uiState.isCorrect) {
                    true -> R.raw.anim_happy
                    false -> R.raw.anim_sad
                    else -> null
                }
            )
        }
    }
}
