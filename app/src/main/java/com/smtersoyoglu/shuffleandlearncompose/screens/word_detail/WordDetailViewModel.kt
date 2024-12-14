package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.data.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WordDetailState())
    val uiState: StateFlow<WordDetailState> = _uiState.asStateFlow()

    // Kelimeyi ID ile Room'dan çekiyoruz
    fun fetchWordById(wordId: Int) {
        viewModelScope.launch {
            try {
                updateState(isLoading = true)
                val word = wordRepository.getWordById(wordId)
                if (word != null) {
                    updateState(word = word, isLoading = false)
                } else {
                    updateState(error = "Word not found", isLoading = false)
                }
            } catch (e: Exception) {
                updateState(error = "Error fetching word: ${e.message}", isLoading = false)
            }
        }
    }

    fun toggleLearnedStatus(word: WordItem) {
        viewModelScope.launch {
            try {
                // Eğer kelime öğrenilmişse, onu unlearned yap
                if (word.isLearned) {
                    wordRepository.markWordAsUnlearned(word)
                } else {
                    // Eğer kelime öğrenilmemişse, onu learned yap
                    wordRepository.markWordAsLearned(word)
                }
                // Yeni durumu yansıtan bir kopya oluştur ve durumu güncelle
                val updatedWord = word.copy(isLearned = !word.isLearned)
                updateState(word = updatedWord)
            } catch (e: Exception) {
                updateState(error = "Failed to update word: ${e.message}")
            }
        }
    }

    private fun updateState(
        isLoading: Boolean = _uiState.value.isLoading,
        word: WordItem? = _uiState.value.word,
        error: String? = _uiState.value.error
    ) {
        _uiState.value = WordDetailState(isLoading, word, error)
    }
}
