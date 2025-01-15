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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val fetchWordsUseCase: FetchWordsUseCase,
    private val getUnlearnedWordsUseCase: GetUnlearnedWordsUseCase,
    private val getLearnedWordsUseCase: GetLearnedWordsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WordState())
    val uiState: StateFlow<WordState> = _uiState.asStateFlow()

    init {
        fetchWordsFromApi()
        observeUnlearnedWords()
    }

    // API'den kelimeleri al ve veritabanına kaydet
    private fun fetchWordsFromApi() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val learnedWordsFlow = getLearnedWordsUseCase()

            lateinit var learnedWords: List<WordItem>
            learnedWordsFlow.collect { result ->
                if (result is Resource.Success) {
                    learnedWords = result.data ?: emptyList()
                }
            }

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
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }


    // Öğrenilmemiş kelimeleri gözlemle
    private fun observeUnlearnedWords() {
        viewModelScope.launch {
            getUnlearnedWordsUseCase()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { exception ->
                    _uiState.update { it.copy(error = "Error fetching unlearned words: ${exception.message}", isLoading = false) }
                }
                .collectLatest { result ->
                    when (result) {
                        is Resource.Success -> _uiState.update {
                            it.copy(words = result.data ?: emptyList(), isLoading = false)
                        }
                        is Resource.Error -> _uiState.update {
                            it.copy(error = result.message ?: "Unknown error", isLoading = false)
                        }
                        is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    }
                }
        }
    }
}





