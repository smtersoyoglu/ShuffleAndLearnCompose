package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.data.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {

    private val _wordState = MutableStateFlow<Resource<WordItem>>(Resource.Loading())
    val wordState: StateFlow<Resource<WordItem>> = _wordState.asStateFlow()

    fun fetchWordById(wordId: Int) {
        viewModelScope.launch {
            _wordState.value = Resource.Loading() // Yüklenme durumu
            val wordsResponse = wordRepository.getWords()

            if (wordsResponse is Resource.Success) {
                val word = wordsResponse.data?.find { it.id == wordId }
                if (word != null) {
                    _wordState.value = Resource.Success(word)
                } else {
                    _wordState.value = Resource.Error("Word not found")
                }
            } else if (wordsResponse is Resource.Error) {
                _wordState.value = Resource.Error(wordsResponse.message ?: "An error occurred")
            }
        }
    }
}
