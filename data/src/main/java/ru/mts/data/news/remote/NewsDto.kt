package ru.mts.data.news.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import ru.mts.data.news.repository.models.News


class NewsDto {
    @Parcelize
    object Request : Parcelable

    @Parcelize
    data class Response(
        @SerializedName("status") var status: String? = null,
        @SerializedName("totalResults") var totalResults: Int? = null,
        @SerializedName("articles") var articles: ArrayList<News>
    ) : Parcelable
}

internal fun NewsDto.Response.toDomain(): ArrayList<News> {
    return articles
}