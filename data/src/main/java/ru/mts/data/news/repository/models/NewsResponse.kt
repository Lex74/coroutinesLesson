package ru.mts.data.news.repository.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("articles") var articles: ArrayList<News> = arrayListOf()
)
