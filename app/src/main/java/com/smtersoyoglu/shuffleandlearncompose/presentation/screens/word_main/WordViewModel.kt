package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.FetchWordsUseCase
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.GetLearnedWordsUseCase
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.GetUnlearnedWordsUseCase
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.WordContract.UiAction
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.WordContract.UiEffect
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.WordContract.UiState
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val fetchWordsUseCase: FetchWordsUseCase,
    private val getUnlearnedWordsUseCase: GetUnlearnedWordsUseCase,
    private val getLearnedWordsUseCase: GetLearnedWordsUseCase,
) : ViewModel() {

    private var _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        fetchWordsAndCheckLearned()
        observeUnlearnedWords()
    }

    fun onAction(action: UiAction) {
        when (action) {
            is UiAction.OnWordClicked -> viewModelScope.launch { emitUiEffect(UiEffect.NavigateToDetail(action.wordId)) }
            is UiAction.ShuffleWords -> shuffleWords()
            is UiAction.ResetShuffle -> resetShuffle()
        }
    }

    private fun fetchWordsAndCheckLearned() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            val learnedWords = getLearnedWordsUseCase()
                .firstOrNull()
                ?.data
                ?: emptyList()

            if (learnedWords.isEmpty()) {
                when (val result = fetchWordsUseCase()) {
                    is Resource.Success -> {
                        updateState {
                            copy(
                                isLoading = false,
                                words = result.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateState {
                            copy(
                                isLoading = false,
                                error = result.message ?: "Error fetching words"
                            )
                        }
                    }
                }
            } else {
                updateState { copy(isLoading = false) }
            }
        }
    }

    private fun observeUnlearnedWords() {
        getUnlearnedWordsUseCase()
            .onStart { updateState { copy(isLoading = true) } }
            .onCompletion { updateState { copy(isLoading = false) } }
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        updateState {
                            copy(
                                words = resource.data ?: emptyList(),
                                shuffledWords = emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateState { copy(error = resource.message ?: "Unknown error") }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun shuffleWords() {
        val shuffled = _uiState.value.words.shuffled()
        updateState { copy(shuffledWords = shuffled) }
    }

    private fun resetShuffle() {
        updateState { copy(shuffledWords = emptyList()) }
    }

    private fun updateState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }

}





