package com.example.omniaxion.domain.repository

import com.example.omniaxion.domain.model.NewsArticle

interface NewsRepository {
    suspend fun getNewsFeed(
        perPage: Int,
        page: Int,
        languageCode: String? = "en",
        countryCode: String? = null,
        title: String? = null
    ): Result<List<NewsArticle>>
}
