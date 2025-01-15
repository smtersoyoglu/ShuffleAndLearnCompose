package com.smtersoyoglu.shuffleandlearncompose.domain.usecase

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import javax.inject.Inject

class CheckWordAnswerUseCase @Inject constructor() {
    operator fun invoke(currentWord: WordItem, userAnswer: String): Boolean {
        return userAnswer.equals(currentWord.english, ignoreCase = true)
    }
}
