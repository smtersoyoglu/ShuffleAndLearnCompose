package com.smtersoyoglu.shuffleandlearncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.smtersoyoglu.shuffleandlearncompose.navigation.WordNavGraph
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.ShuffleAndLearnComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ShuffleAndLearnComposeTheme {
                WordNavGraph()
            }
        }
    }
}

