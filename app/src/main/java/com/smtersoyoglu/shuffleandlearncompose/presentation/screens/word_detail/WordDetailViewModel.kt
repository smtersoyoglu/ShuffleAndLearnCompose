package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.GetWordByIdUseCase
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.ToggleWordLearnedStatusUseCase
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    private val getWordByIdUseCase: GetWordByIdUseCase,
    private val toggleWordLearnedStatusUseCase: ToggleWordLearnedStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WordDetailState())
    val uiState: StateFlow<WordDetailState> = _uiState.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    fun fetchWordById(wordId: Int) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getWordByIdUseCase(wordId)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(word = result.data, isLoading = false, error = null)
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Failed to fetch word"
                        )
                    }
                }
            }
        }
    }

    fun toggleLearnedStatus(word: WordItem) {
        viewModelScope.launch {
            try {
                val updatedWord = toggleWordLearnedStatusUseCase(word)
                _uiState.update {
                    it.copy(word = updatedWord, error = null)
                }
                val message = if (updatedWord.isLearned) {
                    "Kelime Öğrenilenler Listesine Eklendi"
                } else {
                    "Kelime Öğrenilenler Listesinden Çıkarıldı"
                }
                showSnackbarMessage(message)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Failed to update word: ${e.message}")
                }
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        _snackbarMessage.value = message
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }
}


