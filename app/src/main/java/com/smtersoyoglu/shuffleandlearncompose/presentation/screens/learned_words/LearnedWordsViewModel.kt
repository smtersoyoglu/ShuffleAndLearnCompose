package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.GetLearnedWordsUseCase
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.LearnedContract.UiState
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.LearnedContract.UiEffect
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.LearnedContract.UiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnedWordsViewModel @Inject constructor(
    private val getLearnedWordsUseCase: GetLearnedWordsUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: UiAction) {
        when (action) {
            is UiAction.OnWordClicked -> viewModelScope.launch { emitUiEffect(UiEffect.NavigateToDetail(action.wordId)) }
        }
    }

    init {
        fetchLearnedWords()
    }

    private fun fetchLearnedWords() {
        getLearnedWordsUseCase()
            .onStart {
                updateState { copy(isLoading = true) }
            }
            .onCompletion {
                updateState { copy(isLoading = false) }
            }
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        updateState {
                            copy(learnedWords = resource.data ?: emptyList(), isLoading = false)
                        }
                    }

                    is Resource.Error -> {
                        updateState {
                            copy(
                                error = resource.message ?: "An unknown error occurred",
                                isLoading = false
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }


    private fun updateState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}