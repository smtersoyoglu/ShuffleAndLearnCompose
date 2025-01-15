package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.components

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaRegular
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.TurkishTextColor2
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.focusedBorderColor
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.focusedLabelColor
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.unfocusedBorderColor
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.unfocusedLabelColor

@Composable
fun AnswerInputField(
    onAnswerSubmit: (String) -> Unit,
) {
    var userInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val submitAnswer = {
        if (userInput.isBlank()) {
            errorMessage = "Lütfen boş bırakmayınız."
        } else {
            onAnswerSubmit(userInput)
            userInput = ""
            focusManager.clearFocus() // Odağı kaldır ve klavyeyi gizle
        }
    }

    OutlinedTextField(
        value = userInput,
        onValueChange = {
            userInput = it
            errorMessage = ""
        },
        label = { Text("İngilizcesi") },
        textStyle = TextStyle(
            fontFamily = FredokaRegular,
            fontSize = 24.sp,
            color = TurkishTextColor2
        ),
        isError = errorMessage.isNotEmpty(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                submitAnswer()
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

    Spacer(modifier = Modifier.height(10.dp))
    SubmitButton(onClick = {
        submitAnswer()
    })
}
