package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.CheckWordAnswerUseCase
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.FetchShuffledWordsUseCase
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.ResetGameUseCase
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordGameViewModel @Inject constructor(
    private val fetchShuffledWordsUseCase: FetchShuffledWordsUseCase,
    private val checkAnswerUseCase: CheckWordAnswerUseCase,
    private val resetGameUseCase: ResetGameUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WordGameUiState())
    val uiState: StateFlow<WordGameUiState> = _uiState

    private var timerJob: Job? = null

    init {
        startGame()
    }

    private fun startGame() {
        resetGame()
    }

    private fun fetchWords() {
        fetchShuffledWordsUseCase()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onCompletion { _uiState.update { it.copy(isLoading = false) } }
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val shuffledWords = result.data ?: emptyList()
                        _uiState.update {
                            it.copy(
                                wordList = shuffledWords,
                                currentWord = shuffledWords.firstOrNull(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(error = result.message, isLoading = false) }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun checkAnswer(userAnswer: String) {
        val currentWord = _uiState.value.currentWord ?: return
        val isCorrect = checkAnswerUseCase(currentWord, userAnswer)

        _uiState.update {
            it.copy(
                isCorrect = isCorrect,
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount,
                incorrectCount = if (!isCorrect) it.incorrectCount + 1 else it.incorrectCount,
                currentWord = it.wordList?.getOrNull(it.wordList.indexOf(currentWord) + 1)
            )
        }

        if (_uiState.value.currentWord == null) {
            _uiState.update { it.copy(isGameOver = true) }
            timerJob?.cancel()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (time in 60 downTo 0) {
                _uiState.update { it.copy(timer = time) }
                delay(1000)
            }
            _uiState.update { it.copy(isGameOver = true) }
        }
    }

    fun resetGame() {
        timerJob?.cancel()
        viewModelScope.launch {
            val newState = resetGameUseCase().let { it }
            _uiState.value = newState
            fetchWords() // Yeni kelimeleri getir
            startTimer() // Zamanlayıcıyı başlat
        }
    }
}







