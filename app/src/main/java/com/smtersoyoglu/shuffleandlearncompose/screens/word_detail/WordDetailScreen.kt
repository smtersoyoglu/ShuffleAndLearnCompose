package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smtersoyoglu.shuffleandlearncompose.data.model.Word
import com.smtersoyoglu.shuffleandlearncompose.screens.word_detail.components.WordDetailContent
import com.smtersoyoglu.shuffleandlearncompose.util.Resource

@Composable
fun WordDetailScreen(
    navController: NavController,
    wordId: Int,
    viewModel: WordDetailViewModel = hiltViewModel()
) {
    val wordState by viewModel.wordState.collectAsState()

    // ViewModel'den ilgili kelimeyi çek
    LaunchedEffect(wordId) {
        viewModel.fetchWordById(wordId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (wordState) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is Resource.Success -> {
                val word = (wordState as Resource.Success<Word>).data
                word?.let { WordDetailContent(word = it, onBack = { navController.popBackStack() }) }
            }

            is Resource.Error -> {
                Text(
                    text = (wordState as Resource.Error).message ?: "An error occurred",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
