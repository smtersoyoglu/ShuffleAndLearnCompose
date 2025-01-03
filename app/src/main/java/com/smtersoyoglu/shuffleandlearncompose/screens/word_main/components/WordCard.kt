package com.smtersoyoglu.shuffleandlearncompose.screens.word_main.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.CardBackgroundColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.EnglishTextColor2
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaRegular
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaSemiBold
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.TurkishTextColor2

@Composable
fun WordCard(wordItem: WordItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .width(IntrinsicSize.Max)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Card(
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 15.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .height(230.dp)

        ) {
            Column (
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(140.dp))

                Text(
                    text = wordItem.translation,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = TurkishTextColor2,
                        fontFamily = FredokaRegular
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = wordItem.english,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = EnglishTextColor2,
                        fontFamily = FredokaSemiBold

                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Coil kullanarak resmi yükleme
        AsyncImage(
            model = wordItem.imageUrl,
            contentDescription = wordItem.english,
            modifier = Modifier
                .size(size = 180.dp)
                .offset(y = (-50).dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit
        )
    }
}
