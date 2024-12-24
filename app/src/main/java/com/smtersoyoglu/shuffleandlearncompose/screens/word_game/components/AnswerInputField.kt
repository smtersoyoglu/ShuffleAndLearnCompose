package com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaRegular
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.TurkishTextColor2
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.focusedBorderColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.focusedLabelColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.unfocusedBorderColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.unfocusedLabelColor

@Composable
fun AnswerInputField(
    onAnswerSubmit: (String) -> Unit,
) {
    var userInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") } // Hata mesajı için durum
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = userInput,
        onValueChange = { // Kullanıcı girdisi her değiştiğinde güncelleniyor
            userInput = it
            errorMessage = "" // Kullanıcı yazarken hata mesajını temizle
        },
        label = { Text("İngilizcesi") },
        textStyle = TextStyle(
            fontFamily = FredokaRegular,
            fontSize = 24.sp,
            color = TurkishTextColor2
        ),
        isError = errorMessage.isNotEmpty(), // Hata durumunu belirle
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done // Klavyedeki "Done" aksiyonunu gösterir
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (userInput.isBlank()) {
                    errorMessage = "Lütfen boş bırakmayınız."
                } else {
                    onAnswerSubmit(userInput)
                    userInput = ""
                    keyboardController?.hide()
                }
            }
        ),
        placeholder = {
            Text(
                text = "Kelimenin İngilizcesi nedir?",
                style = TextStyle(fontSize = 16.sp),
                fontFamily = FredokaRegular,
                color = TurkishTextColor2
            )
        },
        enabled = true,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(70.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedLabelColor = focusedLabelColor,
            unfocusedLabelColor = unfocusedLabelColor,
        )
    )

    ErrorText(errorMessage = errorMessage, modifier = Modifier.padding(top = 8.dp))

    Spacer(modifier = Modifier.height(12.dp))
    SubmitButton(onClick = {
        if (userInput.isBlank()) {
            errorMessage = "Lütfen boş bırakmayınız."
        } else {
            onAnswerSubmit(userInput)
            userInput = ""
            keyboardController?.hide()
        }
    })
}
