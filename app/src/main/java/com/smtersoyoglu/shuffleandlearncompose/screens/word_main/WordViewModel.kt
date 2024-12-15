package com.smtersoyoglu.shuffleandlearncompose.screens.word_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.data.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WordState())
    val uiState: StateFlow<WordState> = _uiState.asStateFlow()

    init {
        fetchWordsFromApi()
        observeUnlearnedWords()
    }

    private fun fetchWordsFromApi() {
        viewModelScope.launch {
            try {
                if (repository.getLearnedWords().first().isEmpty()) {
                    repository.getWords() // Sadece veritabanında veri yoksa API çağrısı yap

                }
            } catch (e: Exception) {
                updateState(error = "Error fetching words: ${e.message}")
            }

        }
    }

    private fun observeUnlearnedWords() {
        viewModelScope.launch {
            repository.getUnlearnedWordsFlow()
                .collect { words ->
                    if (words.isNotEmpty()) {
                        updateState(words = words, isLoading = false) // Eğer kelimeler varsa, listeyi güncelle
                    } else {
                        updateState(error = "No words available", isLoading = false) // Eğer kelime yoksa, hata mesajını göster
                    }
                }
        }
    }


    private fun updateState(
        isLoading: Boolean = _uiState.value.isLoading,
        words: List<WordItem> = _uiState.value.words,
        error: String? = _uiState.value.error
    ) {
        _uiState.value = WordState(isLoading, words, error)
    }
}