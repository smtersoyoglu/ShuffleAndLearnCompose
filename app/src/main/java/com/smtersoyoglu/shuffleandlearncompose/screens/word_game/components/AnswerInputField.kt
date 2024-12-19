package com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaRegular

@Composable
fun AnswerInputField() {
    OutlinedTextField(
        value = "", // Başlangıçta boş değer.
        onValueChange = { /* Kullanıcı girdisini işlemek için burayı doldurun */ },
        label = { Text("İngilizcesi") },
        textStyle = TextStyle(
            fontFamily = FredokaRegular,
            fontSize = 32.sp
        ),
        placeholder = {
            Text(text = "Kelimenin İngilizcesi nedir?", style = TextStyle(fontSize = 24.sp))
        },
        enabled = true,
        readOnly = false,
        isError = true
    )

}