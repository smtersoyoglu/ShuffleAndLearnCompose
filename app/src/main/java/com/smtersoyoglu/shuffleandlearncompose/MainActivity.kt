package com.smtersoyoglu.shuffleandlearncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ProcessLifecycleOwner
import com.smtersoyoglu.shuffleandlearncompose.navigation.WordNavGraph
import com.smtersoyoglu.shuffleandlearncompose.notification.AppLifecycleObserver
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.ShuffleAndLearnComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Bildirim Servisi ve Lifecycle Observer
    @Inject
    lateinit var lifecycleObserver: AppLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lifecycle Observer'ı kaydet
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)

        enableEdgeToEdge()
        setContent {
            ShuffleAndLearnComposeTheme {
                WordNavGraph()
            }
        }
    }
}

