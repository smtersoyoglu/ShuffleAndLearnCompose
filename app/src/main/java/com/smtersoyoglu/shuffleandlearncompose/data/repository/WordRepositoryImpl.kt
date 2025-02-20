package com.smtersoyoglu.shuffleandlearncompose.data.repository

import com.smtersoyoglu.shuffleandlearncompose.data.local.WordDao
import com.smtersoyoglu.shuffleandlearncompose.data.mappers.WordMapper
import com.smtersoyoglu.shuffleandlearncompose.data.remote.WordService
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import com.smtersoyoglu.shuffleandlearncompose.data.network.safeCall
import com.smtersoyoglu.shuffleandlearncompose.data.network.safeFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordService: WordService,
    private val wordDao: WordDao,
    private val wordMapper: WordMapper,
) : WordRepository {

    // API'den veriyi alıp veritabanına kaydetme
    override suspend fun fetchAndSaveWords(): Resource<List<WordItem>> = safeCall {
        val response = wordService.getWords()
        val domainWords = response.map { wordMapper.fromDtoToDomain(it) }
        wordDao.insertWords(domainWords.map { wordMapper.fromDomainToEntity(it) })
        domainWords

    }

    // Veritabanındaki tüm kelimeleri alma
    override fun getAllWords(): Flow<Resource<List<WordItem>>> = safeFlow {
        wordDao.getAllWords().map { entities ->
            entities.map { wordMapper.fromEntityToDomain(it) }
        }
    }


    // Öğrenilmemiş kelimeleri alma
    override fun getUnlearnedWords(): Flow<Resource<List<WordItem>>> = safeFlow {
        wordDao.getUnlearnedWords().map { entities ->
            entities.map { wordMapper.fromEntityToDomain(it) }
        }
    }


    // Öğrenilmiş kelimeleri alma
    override fun getLearnedWords(): Flow<Resource<List<WordItem>>> = safeFlow {
        wordDao.getLearnedWords().map { entities ->
            entities.map { wordMapper.fromEntityToDomain(it) }
        }
    }

    // Belirli bir kelimeyi ID ile alma
    override suspend fun getWordById(id: Int): Resource<WordItem?> = safeCall {
        val wordEntity = wordDao.getWordById(id)
        wordEntity?.let {
            wordMapper.fromEntityToDomain(it)
        } ?: throw Exception("Word not found with id: $id")
    }

    // Bir kelimeyi öğrenilmiş olarak işaretleme
    override suspend fun markWordAsLearned(word: WordItem) {
        wordDao.updateLearnedStatus(word.id, true)
    }

    // Bir kelimeyi öğrenilmemiş olarak işaretleme
    override suspend fun markWordAsUnlearned(word: WordItem) {
        wordDao.updateLearnedStatus(word.id, false)
    }
}
