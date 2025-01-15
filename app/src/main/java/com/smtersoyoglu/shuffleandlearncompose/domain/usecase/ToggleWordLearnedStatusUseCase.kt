package com.smtersoyoglu.shuffleandlearncompose.domain.usecase

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.repository.WordRepository
import javax.inject.Inject

class ToggleWordLearnedStatusUseCase @Inject constructor(
    private val repository: WordRepository,
) {
    suspend operator fun invoke(word: WordItem): WordItem {
        if (word.isLearned) {
            repository.markWordAsUnlearned(word)
            return word.copy(isLearned = false)
        } else {
            repository.markWordAsLearned(word)
            return word.copy(isLearned = true)
        }
    }
}
