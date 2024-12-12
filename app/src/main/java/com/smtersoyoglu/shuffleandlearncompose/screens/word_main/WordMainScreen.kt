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
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.navigation.Screen
import com.smtersoyoglu.shuffleandlearncompose.screens.word_main.components.WordCard
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.HeaderColor
import com.smtersoyoglu.shuffleandlearncompose.util.Resource

@Composable
fun WordMainScreen(
    viewModel: WordViewModel = hiltViewModel(),
    navController: NavController
) {
    val wordState by viewModel.unlearnedWords.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Word Cards",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                color = HeaderColor
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (wordState) {
            is Resource.Loading -> CircularProgressIndicator()
            is Resource.Success -> {
                val words = (wordState as Resource.Success<List<WordItem>>).data
                if (words != null) {
                    if (words.isEmpty()) {
                        Text("No words available")
                    } else {
                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                            items(words) { word ->
                                WordCard(wordItem = word) {
                                    navController.navigate(Screen.WordDetailScreen.createRoute(word.id,false))
                                }
                            }
                        }
                    }
                }
            }
            is Resource.Error -> Text((wordState as Resource.Error).message ?: "An error occurred")
        }
    }
}
