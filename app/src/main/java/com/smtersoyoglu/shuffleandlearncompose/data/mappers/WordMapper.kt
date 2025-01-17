package com.smtersoyoglu.shuffleandlearncompose.data.mappers

import com.smtersoyoglu.shuffleandlearncompose.data.local.entity.WordEntity
import com.smtersoyoglu.shuffleandlearncompose.data.remote.dto.WordDto
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import javax.inject.Inject

class WordMapper @Inject constructor() {
    // Remote ↔ Domain
    fun fromDtoToDomain(dto: WordDto): WordItem {
        return WordItem(
            id = dto.id,
            translation = dto.translation,
            english = dto.english,
            imageUrl = dto.imageUrl,
            sentence = dto.sentence ?: "",
            isLearned = false
        )
    }

    // Local ↔ Domain
    fun fromEntityToDomain(entity: WordEntity): WordItem {
        return WordItem(
            id = entity.id,
            translation = entity.translation,
            english = entity.english,
            imageUrl = entity.imageUrl,
            sentence = entity.sentence,
            isLearned = entity.isLearned
        )
    }

    fun fromDomainToEntity(domain: WordItem): WordEntity {
        return WordEntity(
            id = domain.id,
            translation = domain.translation,
            english = domain.english,
            imageUrl = domain.imageUrl,
            sentence = domain.sentence,
            isLearned = domain.isLearned
        )
    }
}