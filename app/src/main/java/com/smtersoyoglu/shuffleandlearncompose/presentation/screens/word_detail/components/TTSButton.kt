package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components

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
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.IconTintColor
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
                Log.d("TTS", "Text-to-Speech initialized successfully")
            } else {
                Log.e("TTS", "Text-to-Speech initialization failed with status $status")
            }
        }
        onDispose {
            tts?.stop()
            tts?.shutdown()
            Log.d("TTS", "Text-to-Speech stopped and shutdown")
        }
    }

    // Hoparlör ikonu
    IconButton(
        onClick = {
            // Eğer TTS nesnesi varsa ve başlangıç başarılıysa konuşma işlemini başlat
            if (tts != null && tts?.isSpeaking == false) {
                val result = tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
                if (result == TextToSpeech.ERROR) {
                    Log.e("TTS", "Error occurred while speaking")
                } else {
                    Log.d("TTS", "Speaking: $textToSpeak")
                }
            } else {
                Log.w("TTS", "TTS is either not initialized or already speaking")
            }
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.sound), // Hoparlör ikonu
            contentDescription = "Play pronunciation",
            tint = IconTintColor,
        )
    }
}