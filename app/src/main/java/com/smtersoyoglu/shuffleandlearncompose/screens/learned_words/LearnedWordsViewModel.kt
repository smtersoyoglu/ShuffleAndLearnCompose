package com.smtersoyoglu.shuffleandlearncompose.screens.learned_words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.data.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnedWordsViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LearnedWordsState())
    val uiState: StateFlow<LearnedWordsState> = _uiState.asStateFlow()

    init {
        fetchLearnedWords()
    }

    private fun fetchLearnedWords() {
        viewModelScope.launch {
            repository.getLearnedWords()
                .onStart { updateState(isLoading = true) }
                .catch { exception ->
                    updateState(error = "Error fetching learned words: ${exception.message}", isLoading = false)
                }
                .collect { learnedWords ->
                    updateState(learnedWords = learnedWords, isLoading = false)
                }
        }
    }

    private fun updateState(
        isLoading: Boolean = _uiState.value.isLoading,
        learnedWords: List<WordItem> = _uiState.value.learnedWords,
        error: String? = _uiState.value.error
    ) {
        _uiState.value = LearnedWordsState(isLoading, learnedWords, error)
    }

}