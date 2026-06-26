package com.example.omniaxion.data.api

import com.example.omniaxion.data.model.ApitubeResponseDto
import com.example.omniaxion.data.model.ArticleResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OmniAxisNewsApiService {

    @GET("v1/news/everything")
    suspend fun getEverything(
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1
    ): ApitubeResponseDto<ArticleResponseDto>
}
