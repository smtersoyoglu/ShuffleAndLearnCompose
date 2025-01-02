package com.smtersoyoglu.shuffleandlearncompose.screens.word_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.data.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val repository: WordRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WordState())
    val uiState: StateFlow<WordState> = _uiState.asStateFlow()

    init {
        fetchWordsFromApi()
        observeUnlearnedWords()
    }

    private fun fetchWordsFromApi() {
        viewModelScope.launch {
            updateState(isLoading = true) // Yükleme başladığını belirt

            try {
                if (repository.getLearnedWords().first().isEmpty()) {
                    repository.fetchAndSaveWords() // Sadece veritabanında veri yoksa API çağrısı yap
                }
            } catch (e: Exception) {
                updateState(error = "Error fetching words: ${e.message}")
            } finally {
                updateState(isLoading = false) // Yükleme tamamlandı
            }
        }
    }

    private fun observeUnlearnedWords() {
        viewModelScope.launch {
            repository.getUnlearnedWords()
                .onStart { updateState(isLoading = true) } // Yükleme başladığını belirt
                .catch { e -> updateState(error = e.message ?: "Unknown error", isLoading = false) }
                .collect { words ->
                    if (words.isNotEmpty()) {
                        updateState(
                            words = words,
                            isLoading = false
                        ) // Eğer kelimeler varsa, listeyi güncelle
                    } else {
                        updateState(
                            error = "No words available",
                            isLoading = false
                        ) // Eğer kelime yoksa, hata mesajını göster
                    }
                }
        }
    }

    private fun updateState(
        isLoading: Boolean = _uiState.value.isLoading,
        words: List<WordItem> = _uiState.value.words,
        error: String? = _uiState.value.error,
    ) {
        _uiState.value = WordState(isLoading, words, error)
    }
}
