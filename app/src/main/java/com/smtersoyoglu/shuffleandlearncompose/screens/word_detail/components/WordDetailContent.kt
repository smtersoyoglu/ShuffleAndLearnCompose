package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.EnglishTextColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaBold
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaRegular
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaSemiBold
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.SentenceTestColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.TurkishTextColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.teal_650

@Composable
fun WordDetailContent(
    wordItem: WordItem,
    onLearned: () -> Unit, // Öğrenildi butonu için callback
    onBack: () -> Unit, // Geri dönüş işlemi
    isLearned: Boolean // Kelimenin öğrenildi durumu
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        wordItem.let {
            // Sağ taraftaki renk bloğu
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.5f)
                    .align(Alignment.TopEnd)
                    .background(Color(0xFF5196A2))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    // Geri iconunu sola hizala
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    // Metni yatayda ortala
                    Text(
                        text = buildAnnotatedString {
                            append("Learn ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("This ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Word")
                            }
                        },
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ButtonDefaults.buttonColors(teal_650).containerColor,
                            fontFamily = FredokaBold
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(94.dp))
                AsyncImage(
                    model = wordItem.imageUrl,
                    contentDescription = wordItem.english,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(66.dp))
                Text(
                    text = wordItem.translation,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TurkishTextColor,
                        fontFamily = FredokaRegular,
                        fontSize = 30.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = wordItem.english,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = EnglishTextColor,
                        fontFamily = FredokaSemiBold,
                        fontSize = 36.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = wordItem.sentence,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = SentenceTestColor,
                        fontFamily = FredokaSemiBold,
                        fontSize = 28.sp
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        onLearned()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLearned) Color.Red else teal_650
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (isLearned) "Learned" else "Learn",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        fontFamily = FredokaSemiBold,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}