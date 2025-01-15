package com.smtersoyoglu.shuffleandlearncompose.domain.usecase

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import javax.inject.Inject

class GetWordByIdUseCase @Inject constructor(
    private val wordRepository: WordRepository,
) {
    suspend operator fun invoke(wordId: Int): Resource<WordItem?> {
        return wordRepository.getWordById(wordId)
    }
}
