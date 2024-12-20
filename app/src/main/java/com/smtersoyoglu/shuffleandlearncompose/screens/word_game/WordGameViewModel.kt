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
) : ViewModel() {

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

    fun checkAnswer(userAnswer: String) {
        val currentWord = _uiState.value.currentWord ?: return
        val isCorrect = userAnswer.equals(currentWord.english, ignoreCase = true)

        _uiState.update {
            it.copy(
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount,
                incorrectCount = if (!isCorrect) it.incorrectCount + 1 else it.incorrectCount,
                currentWord = it.wordList.getOrNull(it.wordList.indexOf(currentWord) + 1)
            )
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
        _uiState.update {
            it.copy(
                wordList = emptyList(), // Eğer kelime listesi sıfırlanacaksa
                currentWord = null,
                timer = 60, // Timer'ı sıfırla
                correctCount = 0,
                incorrectCount = 0,
                isGameOver = false
            )
        }
        fetchWords() // Kelimeleri yeniden al
        startTimer() // Zamanlayıcıyı yeniden başlat
    }

}