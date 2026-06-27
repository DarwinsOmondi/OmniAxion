package com.example.omniaxion.data.repository

import com.example.omniaxion.data.api.OmniAxisNewsApiService
import com.example.omniaxion.data.model.ApitubeResponseDto
import com.example.omniaxion.data.model.ArticleResponseDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

class NewsRepositoryImplTest {

    @Mock
    private lateinit var apiService: OmniAxisNewsApiService

    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = NewsRepositoryImpl(apiService)
    }

    @Test
    fun `getNewsFeed returns success when API is successful`() = runTest {
        // Given
        val mockResponse = ApitubeResponseDto<ArticleResponseDto>(
            total = 1,
            perPage = 10,
            currentPage = 1,
            lastPage = 1,
            results = emptyList()
        )
        `when`(apiService.getEverything(any(), any())).thenReturn(mockResponse)

        // When
        val result = repository.getNewsFeed(10, 1)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `getNewsFeed returns failure when API throws exception`() = runTest {
        // Given
        `when`(apiService.getEverything(any(), any())).thenThrow(RuntimeException("API Error"))

        // When
        val result = repository.getNewsFeed(10, 1)

        // Then
        assertTrue(result.isFailure)
    }
}
