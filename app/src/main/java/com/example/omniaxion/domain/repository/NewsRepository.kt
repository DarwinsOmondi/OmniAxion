package com.example.omniaxion.domain.repository

import com.example.omniaxion.domain.model.NewsArticle

interface NewsRepository {
    suspend fun getNewsFeed(perPage: Int, page: Int): Result<List<NewsArticle>>
}
