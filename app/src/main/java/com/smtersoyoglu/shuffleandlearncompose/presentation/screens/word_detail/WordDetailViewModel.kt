package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.GetWordByIdUseCase
import com.smtersoyoglu.shuffleandlearncompose.domain.usecase.ToggleWordLearnedStatusUseCase
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import com.smtersoyoglu.shuffleandlearncompose.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    private val getWordByIdUseCase: GetWordByIdUseCase,
    private val toggleWordLearnedStatusUseCase: ToggleWordLearnedStatusUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _uiState: MutableStateFlow<DetailContract.UiState> =
        MutableStateFlow(DetailContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<DetailContract.UiEffect>() }
    val uiEffect: Flow<DetailContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    private val args = savedStateHandle.toRoute<Screens.WordDetailScreen>()

    init {
        fetchWordById(args.wordId)
    }

    fun onAction(action: DetailContract.UiAction) {
        when (action) {
            is DetailContract.UiAction.ToggleLearnedStatus -> {
                toggleLearnedStatus(action.word)
            }
        }
    }

    private fun fetchWordById(wordId: Int) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getWordByIdUseCase(wordId)) {
                is Resource.Success -> {
                    updateState { copy(word = result.data, isLoading = false, error = null) }
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, error = result.message ?: "Failed to fetch word") }
                }
            }
        }
    }

    private fun toggleLearnedStatus(word: WordItem) {
        viewModelScope.launch {
            try {
                val updatedWord = toggleWordLearnedStatusUseCase(word)
                updateState { copy(word = updatedWord, error = null) }
                val message = if (updatedWord.isLearned) {
                    "Kelime Öğrenilenler Listesine Eklendi"
                } else {
                    "Kelime Öğrenilenler Listesinden Çıkarıldı"
                }
                emitUiEffect(DetailContract.UiEffect.ShowToast(message = message))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Failed to update word: ${e.message}")
                }
            }
        }
    }

    private fun updateState(block: DetailContract.UiState.() -> DetailContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: DetailContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}


