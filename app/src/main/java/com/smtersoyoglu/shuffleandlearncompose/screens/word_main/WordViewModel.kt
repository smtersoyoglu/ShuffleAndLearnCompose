package com.smtersoyoglu.shuffleandlearncompose.screens.word_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.data.model.Word
import com.smtersoyoglu.shuffleandlearncompose.data.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    private val _wordState = MutableStateFlow<Resource<List<Word>>>(Resource.Loading())
    val wordList: StateFlow<Resource<List<Word>>> = _wordState.asStateFlow()

    init {
        fetchWords()

    }

    private fun fetchWords() {
        viewModelScope.launch {
           _wordState.value = Resource.Loading()
            _wordState.value = repository.getWords()
        }
    }
}