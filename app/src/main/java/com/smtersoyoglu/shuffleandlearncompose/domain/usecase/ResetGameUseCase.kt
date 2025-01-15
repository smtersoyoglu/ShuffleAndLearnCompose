package com.smtersoyoglu.shuffleandlearncompose.domain.usecase

import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.WordGameUiState
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ResetGameUseCase @Inject constructor(
    private val fetchShuffledWordsUseCase: FetchShuffledWordsUseCase,
) {
    suspend operator fun invoke(): WordGameUiState {
        fetchShuffledWordsUseCase().first { it is Resource.Success }.let { shuffledWords ->
            val data = (shuffledWords as Resource.Success).data!!
            return WordGameUiState(
                wordList = data,
                currentWord = data.firstOrNull(),
                timer = 60
            )
        }
    }
}
