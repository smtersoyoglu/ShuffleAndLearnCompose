package com.smtersoyoglu.shuffleandlearncompose.data.model

import com.smtersoyoglu.shuffleandlearncompose.R

data class Word(
    val id: Int,
    val translation: String,
    val english: String,
    val imageUrl: Int,
    //val imageUrl: String,
    val sentence: String
)


// getWordList() fonksiyonu: Uygulamada kullanılacak kelime listesini döner
fun getWordList(): List<Word> {
    return listOf(
        Word(
            1,
            "Ördek",
            "Duck",
            R.drawable.duck_card,
            sentence = "The duck is swimming in the pond."
        ),
        Word(
            2,
            "Köpek",
            "Dog",
            R.drawable.dog_card,
            sentence = "The dog is playing with a ball in the garden."
        ),
        Word(
            3,
            "Kedi",
            "Cat",
            R.drawable.cat_card,
            sentence = "The cat is sleeping on the windowsill."
        ),
        Word(4,
            "Elma",
            "Apple",
            R.drawable.apple_card,
            sentence = "She is eating a red apple."),
        Word(
            5,
            "Çiçek",
            "Flower",
            R.drawable.flower_card,
            sentence = "The flower is blooming in the garden."
        ),
    )
}