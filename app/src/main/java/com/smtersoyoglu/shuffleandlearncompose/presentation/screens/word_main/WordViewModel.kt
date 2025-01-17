package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.FetchWordsUseCase
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.GetLearnedWordsUseCase
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.GetUnlearnedWordsUseCase
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val fetchWordsUseCase: FetchWordsUseCase,
    private val getUnlearnedWordsUseCase: GetUnlearnedWordsUseCase,
    private val getLearnedWordsUseCase: GetLearnedWordsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WordState())
    val uiState: StateFlow<WordState> = _uiState.asStateFlow()

    init {
        fetchWordsAndCheckLearned()
        observeUnlearnedWords()
    }

    private fun fetchWordsAndCheckLearned() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val learnedWords = getLearnedWordsUseCase()
                .firstOrNull()
                ?.data
                ?: emptyList()

            if (learnedWords.isEmpty()) {
                when (val result = fetchWordsUseCase()) {
                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Error fetching words"
                            )
                        }
                    }
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    // Öğrenilmemiş kelimeleri gözlemle
    private fun observeUnlearnedWords() {
        getUnlearnedWordsUseCase()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onCompletion { _uiState.update { it.copy(isLoading = false) } }
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.update { it.copy(words = resource.data ?: emptyList()) }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(error = resource.message ?: "Unknown error") }
                    }
                }
            }.launchIn(viewModelScope)
    }
}





