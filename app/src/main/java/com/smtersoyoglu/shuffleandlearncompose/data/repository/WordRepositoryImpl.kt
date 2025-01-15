package com.smtersoyoglu.shuffleandlearncompose.data.repository

import com.smtersoyoglu.shuffleandlearncompose.data.local.WordDao
import com.smtersoyoglu.shuffleandlearncompose.data.mappers.WordMapper
import com.smtersoyoglu.shuffleandlearncompose.data.remote.WordService
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.domain.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
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
        return try {
            // API'den gelen DTO'ları Domain modeline çevir
            val response = wordService.getWords()
            val domainWords = response.map { wordMapper.fromDtoToDomain(it) }

            // Domain modelini Room için Entity'ye çevirip kaydet
            wordDao.insertWords(domainWords.map { wordMapper.fromDomainToEntity(it) })

            // Domain modelini döndür
            Resource.Success(domainWords)
        } catch (e: Exception) {
            Resource.Error("Error fetching words: ${e.message}")
        }
    }

    // Veritabanındaki tüm kelimeleri alma
    override fun getAllWords(): Flow<Resource<List<WordItem>>> = flow {
        emit(Resource.Loading())
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
        emit(Resource.Loading())
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
        emit(Resource.Loading())
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
        return try {
            val wordEntity = wordDao.getWordById(id)
            if (wordEntity != null) {
                Resource.Success(wordMapper.fromEntityToDomain(wordEntity)) // Domain modelini döndür
            } else {
                Resource.Error("Word not found with id: $id")
            }
        } catch (e: Exception) {
            Resource.Error("Error fetching word by id: ${e.message}")
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
