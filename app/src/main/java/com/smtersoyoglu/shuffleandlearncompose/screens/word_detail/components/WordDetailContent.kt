package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem

@Composable
fun WordDetailContent(
    wordItem: WordItem,
    onLearned: () -> Unit, // Öğrenildi butonu için callback
    onBack: () -> Unit, // Geri dönüş işlemi
    isLearned: Boolean // Kelimenin öğrenildi durumu
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Sağ üstteki renk bloğu
        ColorBlock()

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HeaderSection(onBack = onBack)

            TTSButton(textToSpeak = wordItem.english)

            Spacer(modifier = Modifier.height(12.dp))

            WordImage(imageUrl = wordItem.imageUrl, description = wordItem.english)

            Spacer(modifier = Modifier.height(44.dp))

            WordDetailsText(
                translation = wordItem.translation,
                english = wordItem.english,
                sentence = wordItem.sentence
            )

            LearnButton(isLearned = isLearned, onLearned = onLearned)
        }
    }
}