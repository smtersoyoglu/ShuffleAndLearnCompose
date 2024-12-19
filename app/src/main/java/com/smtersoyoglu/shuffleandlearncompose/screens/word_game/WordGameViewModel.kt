package com.smtersoyoglu.shuffleandlearncompose.screens.word_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.data.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordGameViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(WordGameUiState())
    val uiState: StateFlow<WordGameUiState> = _uiState

    private var timerJob: Job? = null

    init {
        startTimer()
        fetchWords()

    }

    private fun fetchWords() {
        viewModelScope.launch {
            wordRepository.getAllWords().collect { words ->
                // Burada her defasında kelimeleri karıştırıyoruz
                val shuffledWords = words.shuffled()
                _uiState.update {
                    it.copy(
                        wordList = shuffledWords, // Kelimeleri karıştır
                        currentWord = shuffledWords.firstOrNull() // İlk kelimeyi ata
                    )
                }
            }
        }
    }


    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (time in 60 downTo 0) {
                _uiState.update { it.copy(timer = time) }
                delay(1000)
            }
        }
        _uiState.update { it.copy(isGameOver = true) }
    }


}