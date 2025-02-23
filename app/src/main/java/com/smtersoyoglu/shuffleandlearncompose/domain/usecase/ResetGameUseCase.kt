package com.smtersoyoglu.shuffleandlearncompose.domain.usecase

import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.GameContract
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ResetGameUseCase @Inject constructor(
    private val fetchShuffledWordsUseCase: FetchShuffledWordsUseCase,
) {
    suspend operator fun invoke(): GameContract.UiState {
        fetchShuffledWordsUseCase().first { it is Resource.Success }.let { shuffledWords ->
            val data = (shuffledWords as Resource.Success).data!!
            return GameContract.UiState(
                wordList = data,
                currentWord = data.firstOrNull(),
                timer = 60
            )
        }
    }
}
