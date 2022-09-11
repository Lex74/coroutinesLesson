package ru.ermolnik.news

import ru.mts.data.news.repository.models.News

sealed class NewsState {
    object Loading: NewsState()
    data class Error(val throwable: Throwable): NewsState()
    data class Content(val news: ArrayList<News>): NewsState()
}