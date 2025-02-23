package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.CardBackgroundColor
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaSemiBold
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.TurkishTextColor2

@Composable
fun WordGameCard(wordItem: WordItem) {

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .width(IntrinsicSize.Max),
        contentAlignment = Alignment.Center
    ) {
        // Kartların stack düzeni
        val cards = listOf(
            Triple((-6).dp, 18.dp, -6f),
            Triple(12.dp, 24.dp, 5f),
            Triple((-4).dp, 18.dp, 5f),
            Triple(4.dp, 24.dp, -2f)
        )

        cards.forEachIndexed { index, (xOffset, yOffset, rotationZ) ->
            Card(
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp + index * 2.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundColor.copy(alpha = 0.5f)),
                modifier = Modifier
                    .offset(x = xOffset, y = yOffset)
                    .width(240.dp - index * 10.dp)
                    .height(230.dp - index * 5.dp)
                    .graphicsLayer(rotationZ = rotationZ)
            ) {}
        }

        Card(
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 15.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            modifier = Modifier
                .padding(top = 30.dp)
                .width(240.dp)
                .height(230.dp)

        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(170.dp))

                Text(
                    text = wordItem.translation,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = TurkishTextColor2,
                        fontFamily = FredokaSemiBold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        AsyncImage(
            model = wordItem.url,
            contentDescription = wordItem.english,
            modifier = Modifier
                .size(size = 240.dp)
                .offset(y = (-60).dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
fun GameWordCardPreview() {
    WordGameCard(
        wordItem = WordItem(
            id = 1,
            translation = "Elma",
            english = "Apple",
            url = "",
            sentence = "",
            isLearned = false
        )

    )
}