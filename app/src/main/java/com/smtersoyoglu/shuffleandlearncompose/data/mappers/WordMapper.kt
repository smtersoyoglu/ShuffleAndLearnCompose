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
            translation = dto.translation.orEmpty(),
            english = dto.english.orEmpty(),
            url = dto.url.orEmpty(),
            sentence = dto.sentence.orEmpty(),
            isLearned = false
        )
    }

    // Local ↔ Domain
    fun fromEntityToDomain(entity: WordEntity): WordItem {
        return WordItem(
            id = entity.id,
            translation = entity.translation.orEmpty(),
            english = entity.english.orEmpty(),
            url = entity.url.orEmpty(),
            sentence = entity.sentence.orEmpty(),
            isLearned = entity.isLearned
        )
    }

    fun fromDomainToEntity(domain: WordItem): WordEntity {
        return WordEntity(
            id = domain.id,
            translation = domain.translation,
            english = domain.english,
            url = domain.url,
            sentence = domain.sentence,
            isLearned = domain.isLearned
        )
    }
}