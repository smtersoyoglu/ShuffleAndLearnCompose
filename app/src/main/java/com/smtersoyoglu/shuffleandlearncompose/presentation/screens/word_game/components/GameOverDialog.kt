package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaRegular
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaSemiBold

@Composable
fun GameOverDialog(
    correctCount: Int,
    incorrectCount: Int,
    onRetry: () -> Unit,
    onExit: () -> Unit,
) {
    Dialog(
        onDismissRequest = { }
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(top = 24.dp, bottom = 24.dp, start = 32.dp, end = 32 .dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Oyun Bitti!",
                    fontFamily = FredokaSemiBold,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Skorunuz:\nDoğru: $correctCount\nYanlış: $incorrectCount",
                    fontSize = 24.sp,
                    fontFamily = FredokaRegular,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Text(
                    text = "Tekrar Oynamak İstermisiniz ?",
                    fontSize = 27.sp,
                    fontFamily = FredokaRegular,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ElevatedButton(
                        onClick = onRetry,
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            "Evet",
                            fontSize = 20.sp,
                            fontFamily = FredokaRegular,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    ElevatedButton(
                        onClick = onExit,
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(
                            "Hayır",
                            fontSize = 20.sp,
                            fontFamily = FredokaRegular,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun GameOverDialogPreview() {
    GameOverDialog(correctCount = 10, incorrectCount = 5, onRetry = {}, onExit = {})
}
