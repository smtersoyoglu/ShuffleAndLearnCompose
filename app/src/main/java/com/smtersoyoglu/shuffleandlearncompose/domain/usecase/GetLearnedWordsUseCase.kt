package com.smtersoyoglu.shuffleandlearncompose.domain.usecase

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLearnedWordsUseCase @Inject constructor(
    private val wordRepository: WordRepository,
) {
    operator fun invoke(): Flow<Resource<List<WordItem>>> {
        return wordRepository.getLearnedWords()
    }
}
