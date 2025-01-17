package com.smtersoyoglu.shuffleandlearncompose.data.repository

import com.smtersoyoglu.shuffleandlearncompose.data.local.WordDao
import com.smtersoyoglu.shuffleandlearncompose.data.mappers.WordMapper
import com.smtersoyoglu.shuffleandlearncompose.data.remote.WordService
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import com.smtersoyoglu.shuffleandlearncompose.data.network.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordService: WordService,
    private val wordDao: WordDao,
    private val wordMapper: WordMapper,
) : WordRepository {

    // API'den veriyi alıp veritabanına kaydetme
    override suspend fun fetchAndSaveWords(): Resource<List<WordItem>> {
        return safeCall {
            val response = wordService.getWords()
            val domainWords = response.map { wordMapper.fromDtoToDomain(it) }
            wordDao.insertWords(domainWords.map { wordMapper.fromDomainToEntity(it) })
            domainWords
        }
    }

    // Veritabanındaki tüm kelimeleri alma
    override fun getAllWords(): Flow<Resource<List<WordItem>>> = flow {
        wordDao.getAllWords()
            .map { entities ->
                Resource.Success(entities.map { wordMapper.fromEntityToDomain(it) })
            }
            .catch { exception ->
                emit(Resource.Error("Error fetching all words: ${exception.message}"))
            }
            .collect { emit(it) }
    }


    // Öğrenilmemiş kelimeleri alma
    override fun getUnlearnedWords(): Flow<Resource<List<WordItem>>> = flow {
        wordDao.getUnlearnedWords()
            .map { entities ->
                Resource.Success(entities.map { wordMapper.fromEntityToDomain(it) }) // Entity -> Domain
            }
            .catch { exception ->
                emit(Resource.Error("Error fetching unlearned words: ${exception.message}"))
            }
            .collect { emit(it) }
    }


    // Öğrenilmiş kelimeleri alma
    override fun getLearnedWords(): Flow<Resource<List<WordItem>>> = flow {
        wordDao.getLearnedWords()
            .map { entities ->
                Resource.Success(entities.map { wordMapper.fromEntityToDomain(it) })  // Entity -> Domain
            }
            .catch { exception ->
                emit(Resource.Error("Error fetching learned words: ${exception.message}"))
            }
            .collect { emit(it) }
    }

    // Belirli bir kelimeyi ID ile alma
    override suspend fun getWordById(id: Int): Resource<WordItem?> {
        return safeCall {
            val wordEntity = wordDao.getWordById(id)
            wordEntity?.let {
                wordMapper.fromEntityToDomain(it)
            } ?: throw Exception("Word not found with id: $id")
        }
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
