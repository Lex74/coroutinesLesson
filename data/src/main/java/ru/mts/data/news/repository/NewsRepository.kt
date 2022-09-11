package ru.mts.data.news.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mts.data.news.db.NewsLocalDataSource
import ru.mts.data.news.db.toDomain
import ru.mts.data.news.remote.NewsRemoteDataSource
import ru.mts.data.news.remote.toDomain
import ru.mts.data.news.repository.models.News
import ru.mts.data.news.repository.models.toDbEntity
import ru.mts.data.utils.*

class NewsRepository(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource
) {
    fun getNews(): Flow<Result<ArrayList<News>, Throwable>> {
        return flow {
            val localNewsResult = newsLocalDataSource.getNews()
                .mapSuccess {
                    ArrayList(it.map { item -> item.toDomain() })
                }
            if (localNewsResult.getOrThrow().isNotEmpty()) {
                emit(localNewsResult)
            } else {
                newsRemoteDataSource.getNews()
                    .mapSuccess { it.toDomain() }
                    .doOnSuccess {
                        emit(Result.Success(it))
                        it.forEach { item ->
                            newsLocalDataSource.saveNews(item.toDbEntity())
                        }
                    }
                    .doOnError {
                        emit(Result.Error(it))
                    }
            }
        }
    }

    fun getRemoteNews(): Flow<Result<ArrayList<News>, Throwable>> {
        return flow {
            newsRemoteDataSource.getNews()
                .mapSuccess { it.toDomain() }
                .doOnSuccess {
                    emit(Result.Success(it))
                    it.forEach { item ->
                        newsLocalDataSource.saveNews(item.toDbEntity())
                    }
                }
                .doOnError {
                    emit(Result.Error(it))
                }
        }
    }

}
