package com.smtersoyoglu.shuffleandlearncompose.screens.word_main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.data.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    // Room'dan öğrenilmemiş kelimeleri sürekli takip eden bir Flow
    val unlearnedWords: StateFlow<Resource<List<WordItem>>> = repository.getUnlearnedWordsFlow()
        .map { words ->
            if (words.isNotEmpty()) {
                Resource.Success(words)
            } else {
                Resource.Error("No words available")
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Resource.Loading())

    init {
        fetchWordsFromApi()
    }

    private fun fetchWordsFromApi() {
        viewModelScope.launch {
            try {
                // Retrofit'ten verileri çekip Room'a kaydet
                repository.getWords()
            } catch (e: Exception) {
                Log.e("WordViewModel", "Error fetching words from API: ${e.message}")
            }
        }
    }
}