package com.smtersoyoglu.shuffleandlearncompose.domain.usecase

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.repository.WordRepository
import javax.inject.Inject

class MarkWordAsLearnedUseCase @Inject constructor(
    private val wordRepository: WordRepository,
) {
    suspend operator fun invoke(word: WordItem) {
        wordRepository.markWordAsLearned(word)
    }
}
