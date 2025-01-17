package com.smtersoyoglu.shuffleandlearncompose

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.smtersoyoglu.shuffleandlearncompose.navigation.WordNavGraph
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.BackgroundColor
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.ShuffleAndLearnComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = BackgroundColor.toArgb()

//        enableEdgeToEdge()
        setContent {
            ShuffleAndLearnComposeTheme {
                WordNavGraph()
            }
        }
    }
}

