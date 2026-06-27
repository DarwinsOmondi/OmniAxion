package com.example.omniaxion.data.repository

import com.example.omniaxion.data.api.OmniAxisNewsApiService
import com.example.omniaxion.data.mapper.toDomain
import com.example.omniaxion.domain.model.NewsArticle
import com.example.omniaxion.domain.repository.NewsRepository
import timber.log.Timber
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: OmniAxisNewsApiService
) : NewsRepository {

    override suspend fun getNewsFeed(
        perPage: Int,
        page: Int,
        languageCode: String?,
        countryCode: String?,
        title: String?
    ): Result<List<NewsArticle>> {
        return try {
            Timber.d("Fetching news feed: page=$page, perPage=$perPage, language=$languageCode, country=$countryCode, title=$title")
            val response = apiService.getEverything(
                languageCode = languageCode,
                countryCode = countryCode,
                title = title,
                perPage = perPage,
                page = page
            )
            val articles = response.results.map { it.toDomain() }
            Timber.i("Successfully fetched ${articles.size} articles")
            Result.success(articles)
        } catch (e: Exception) {
            Timber.e(e, "Error fetching news feed")
            Result.failure(e)
        }
    }
}
