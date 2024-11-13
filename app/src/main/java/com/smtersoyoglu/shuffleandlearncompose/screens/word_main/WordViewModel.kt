package com.smtersoyoglu.shuffleandlearncompose.screens.word_main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.data.model.Word
import com.smtersoyoglu.shuffleandlearncompose.data.retrofit.WordService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordService: WordService
) : ViewModel() {

    private val _wordList = MutableStateFlow<List<Word>>(emptyList())
    val wordList: StateFlow<List<Word>> = _wordList.asStateFlow()

    init {
        fetchWords()

    }

    private fun fetchWords() {
        viewModelScope.launch {
            try {
                val words = wordService.getWords()
                _wordList.value = words
            } catch (e: Exception) {
                // Hata yönetimi
                Log.e("WordViewModel", "Error fetching words: ${e.message}")
            }
        }
    }
}