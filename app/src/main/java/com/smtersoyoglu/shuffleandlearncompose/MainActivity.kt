package com.smtersoyoglu.shuffleandlearncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smtersoyoglu.shuffleandlearncompose.screens.word_main.WordMainScreen
import com.smtersoyoglu.shuffleandlearncompose.screens.word_main.WordViewModel
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.ShuffleAndLearnComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Hilt tarafından ViewModel'i enjekte ediyoruz
    private val wordViewModel: WordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShuffleAndLearnComposeTheme {


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WordMainScreen(modifier = Modifier.padding(innerPadding)
                        , viewModel = wordViewModel)
                }
            }
        }
    }
}

