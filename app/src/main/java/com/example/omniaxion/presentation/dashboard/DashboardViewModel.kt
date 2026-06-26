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
            Timber.d("Loading news in ViewModel")
            
            getTimelineNewsUseCase()
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
}

data class DashboardState(
    val isLoading: Boolean = false,
    val articles: List<NewsArticle> = emptyList(),
    val error: String? = null
)
