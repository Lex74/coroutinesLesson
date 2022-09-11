package ru.mts.data.news.db

import androidx.room.*


@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAll(): List<NewsEntity>?

    @Query("SELECT * FROM news WHERE title = :title")
    fun getByTitle(title: String): NewsEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(news: NewsEntity?)

    @Update
    fun update(news: NewsEntity?)

    @Delete
    fun delete(news: NewsEntity?)
}