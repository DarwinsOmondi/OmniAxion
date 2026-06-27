package com.example.omniaxion.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omniaxion.domain.model.NewsArticle
import com.example.omniaxion.domain.usecase.GetTimelineNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getTimelineNewsUseCase: GetTimelineNewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            Timber.d("Loading news in ViewModel with filters: ${_uiState.value.filters}")
            
            val filters = _uiState.value.filters
            getTimelineNewsUseCase(
                languageCode = filters.languageCode,
                countryCode = filters.countryCode,
                title = filters.searchQuery
            )
                .onSuccess { articles ->
                    Timber.i("ViewModel received ${articles.size} articles")
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            articles = articles
                        ) 
                    }
                }
                .onFailure { error ->
                    Timber.e(error, "ViewModel error loading news")
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown error occurred"
                        ) 
                    }
                }
        }
    }

    fun updateFilters(
        languageCode: String? = _uiState.value.filters.languageCode,
        countryCode: String? = _uiState.value.filters.countryCode,
        searchQuery: String? = _uiState.value.filters.searchQuery
    ) {
        _uiState.update { 
            it.copy(
                filters = it.filters.copy(
                    languageCode = languageCode,
                    countryCode = countryCode,
                    searchQuery = searchQuery
                )
            )
        }
        loadNews()
    }
}

data class DashboardState(
    val isLoading: Boolean = false,
    val articles: List<NewsArticle> = emptyList(),
    val error: String? = null,
    val filters: NewsFilters = NewsFilters()
)

data class NewsFilters(
    val languageCode: String? = "en",
    val countryCode: String? = null,
    val searchQuery: String? = null
)
