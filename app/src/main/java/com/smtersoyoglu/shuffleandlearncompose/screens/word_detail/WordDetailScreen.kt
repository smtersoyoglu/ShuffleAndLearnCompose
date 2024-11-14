package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.smtersoyoglu.shuffleandlearncompose.screens.word_main.WordViewModel
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.teal_650

@Composable
fun WordDetailScreen(navController: NavController, wordId: Int, viewModel: WordViewModel) {
    // Kelimeyi ViewModel'den alıyoruz
    val word = viewModel.wordList.collectAsState().value.find { it.id == wordId }

    word?.let {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = it.english,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = it.imageUrl,
                    contentDescription = it.english,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = it.translation,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = it.sentence,
                    style = MaterialTheme.typography.bodyLarge
                )

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(teal_650),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Learned",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    } ?: run {
        // Hata mesajı veya boş içerik gösterilebilir
        Text("Word not found", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
    }
}
