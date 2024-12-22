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
    private val wordRepository: WordRepository,
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
                isCorrect = isCorrect, // Doğru/yanlış durumu
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount,
                incorrectCount = if (!isCorrect) it.incorrectCount + 1 else it.incorrectCount,
                currentWord = it.wordList.getOrNull(it.wordList.indexOf(currentWord) + 1)
            )
        }
    }

    private fun startTimer() {
        timerJob?.cancel() // Eski timer job'u iptal et
        timerJob = viewModelScope.launch {
            for (time in 60 downTo 0) {
                _uiState.update { it.copy(timer = time) }
                delay(1000)
            }
            _uiState.update { it.copy(isGameOver = true) }
        }
    }

    fun resetGame() {
        _uiState.value = WordGameUiState() // Varsayılan değerlere sıfırla
        fetchWords() // Yeni kelimeleri getir
        startTimer() // Zamanlayıcıyı başlat
    }

}