package com.smtersoyoglu.shuffleandlearncompose.domain.usecase

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchShuffledWordsUseCase @Inject constructor(
    private val wordRepository: WordRepository,
) {
    operator fun invoke(): Flow<Resource<List<WordItem>>> {
        return wordRepository.getAllWords().map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data?.shuffled() ?: emptyList())
                is Resource.Error -> Resource.Error(
                    resource.message ?: "An error occurred while fetching words."
                )
                is Resource.Loading -> Resource.Loading()
            }
        }
    }
}
