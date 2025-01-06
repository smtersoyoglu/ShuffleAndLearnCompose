package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail.components

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.smtersoyoglu.shuffleandlearncompose.R
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.IconTintColor
import java.util.Locale

@Composable
fun TTSButton(textToSpeak: String) {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

    // TTS'i başlat
    DisposableEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US // İngilizce dil ayarı
            } else {
                Log.e("TTS", "Text-to-Speech initialization failed")
            }
        }
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }

    // Hoparlör ikonu
    IconButton(
        onClick = {
            tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.sound), // Hoparlör ikonu için bir drawable ekle
            contentDescription = "Play pronunciation",
            tint = IconTintColor,
        )
    }
}