package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.CheckWordAnswerUseCase
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.FetchShuffledWordsUseCase
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.ResetGameUseCase
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.GameContract.UiState
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.GameContract.UiAction
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.GameContract.UiEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordGameViewModel @Inject constructor(
    private val fetchShuffledWordsUseCase: FetchShuffledWordsUseCase,
    private val checkAnswerUseCase: CheckWordAnswerUseCase,
    private val resetGameUseCase: ResetGameUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    private var timerJob: Job? = null

    init {
        onAction(UiAction.StartGame)
    }

    fun onAction(action: UiAction) {
        when (action) {
            is UiAction.StartGame -> startGame()
            is UiAction.ResetGame -> resetGame()
            is UiAction.SubmitAnswer -> checkAnswer(action.userAnswer)
            is UiAction.ExitGame -> {
                viewModelScope.launch { emitUiEffect(UiEffect.NavigateToMainScreen) }
            }
        }
    }

    private fun startGame() {
        resetGame()
    }

    private fun fetchWords() {
        fetchShuffledWordsUseCase()
            .onStart { updateState { copy(isLoading = true) } }
            .onCompletion { updateState { copy(isLoading = false) } }
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val shuffledWords = result.data ?: emptyList()
                        updateState {
                            copy(
                                wordList = shuffledWords,
                                currentWord = shuffledWords.firstOrNull(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateState { copy(error = result.message, isLoading = false) }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun checkAnswer(userAnswer: String) {
        val currentWord = _uiState.value.currentWord ?: return
        val isCorrect = checkAnswerUseCase(currentWord, userAnswer)

        updateState {
            copy(
                isCorrect = isCorrect,
                correctCount = if (isCorrect) correctCount + 1 else correctCount,
                incorrectCount = if (!isCorrect) incorrectCount + 1 else incorrectCount,
                currentWord = wordList?.getOrNull(wordList.indexOf(currentWord) + 1)
            )
        }

        if (_uiState.value.currentWord == null || _uiState.value.timer <= 0) {
            updateState { copy(isGameOver = true) }
            timerJob?.cancel()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (time in 60 downTo 0) {
                updateState { copy(timer = time) }
                delay(1000)
            }
            updateState { copy(isGameOver = true) }
        }
    }

    private fun resetGame() {
        timerJob?.cancel()
        viewModelScope.launch {
            resetGameUseCase().let { newState ->
                _uiState.value = newState
            }
            fetchWords()
            startTimer()
        }
    }

    private fun updateState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }

}







