package com.example.omniaxion.presentation.dashboard

import com.example.omniaxion.domain.usecase.GetTimelineNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    @Mock
    private lateinit var getTimelineNewsUseCase: GetTimelineNewsUseCase

    private lateinit var viewModel: DashboardViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        // ViewModel init calls loadNews, so we mock it before instantiation
        `when`(getTimelineNewsUseCase(any(), any())).thenReturn(Result.success(emptyList()))
        viewModel = DashboardViewModel(getTimelineNewsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadNews updates state to success when use case is successful`() = runTest {
        // Given
        val articles = emptyList<com.example.omniaxion.domain.model.NewsArticle>()
        `when`(getTimelineNewsUseCase(any(), any())).thenReturn(Result.success(articles))

        // When
        viewModel.loadNews()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(articles, viewModel.uiState.value.articles)
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals(null, viewModel.uiState.value.error)
    }

    @Test
    fun `loadNews updates state to error when use case fails`() = runTest {
        // Given
        val errorMessage = "Network Error"
        `when`(getTimelineNewsUseCase(any(), any())).thenReturn(Result.failure(RuntimeException(errorMessage)))

        // When
        viewModel.loadNews()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(errorMessage, viewModel.uiState.value.error)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }
}
