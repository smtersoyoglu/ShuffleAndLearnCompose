package com.smtersoyoglu.shuffleandlearncompose.domain.usecase

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import javax.inject.Inject

class FetchWordsUseCase @Inject constructor(
    private val wordRepository: WordRepository,
) {
    suspend operator fun invoke(): Resource<List<WordItem>> {
        return wordRepository.fetchAndSaveWords()
    }
}
