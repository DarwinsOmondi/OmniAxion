package com.example.omniaxion.domain.usecase

import com.example.omniaxion.domain.model.NewsArticle
import com.example.omniaxion.domain.repository.NewsRepository
import javax.inject.Inject

class GetTimelineNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(perPage: Int = 10, page: Int = 1): Result<List<NewsArticle>> {
        return repository.getNewsFeed(perPage, page)
    }
}
