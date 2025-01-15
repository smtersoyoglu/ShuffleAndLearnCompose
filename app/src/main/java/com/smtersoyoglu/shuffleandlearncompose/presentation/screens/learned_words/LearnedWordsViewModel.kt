package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.GetLearnedWordsUseCase
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnedWordsViewModel @Inject constructor(
    private val getLearnedWordsUseCase: GetLearnedWordsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LearnedWordsState())
    val uiState: StateFlow<LearnedWordsState> = _uiState.asStateFlow()

    init {
        fetchLearnedWords()
    }

    private fun fetchLearnedWords() {
        viewModelScope.launch {
            getLearnedWordsUseCase()
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    learnedWords = resource.data ?: emptyList(),
                                    isLoading = false,
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = resource.message,
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                    }
                }
        }
    }
}