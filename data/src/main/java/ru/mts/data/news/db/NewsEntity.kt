package ru.mts.data.news.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mts.data.news.repository.models.News

@Entity(tableName = "news")
data class NewsEntity(
//    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "author") var author: String? = null,
    @PrimaryKey @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "url") var url: String? = null,
    @ColumnInfo(name = "urlToImage") var urlToImage: String? = null,
    @ColumnInfo(name = "publishedAt") var publishedAt: String? = null,
    @ColumnInfo(name = "content") var content: String? = null
)

fun NewsEntity.toDomain() =
    News(author, title, description, url, urlToImage, publishedAt, content)