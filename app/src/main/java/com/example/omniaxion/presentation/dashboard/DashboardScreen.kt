package com.example.omniaxion.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omniaxion.domain.model.NewsArticle
import com.example.omniaxion.presentation.common.ErrorScreen
import com.example.omniaxion.ui.theme.OmniAxionTheme
import com.example.omniaxion.ui.theme.OmniAxisColors

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onArticleClick: (NewsArticle) -> Unit,
    onMapClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var showFilters by remember { mutableStateOf(false) }

    if (showFilters) {
        FilterBottomSheet(
            currentFilters = state.filters,
            onApplyFilters = { lang, country, query ->
                viewModel.updateFilters(lang, country, query)
            },
            onDismiss = { showFilters = false }
        )
    }

    DashboardContent(
        state = state,
        onArticleClick = onArticleClick,
        onMapClick = onMapClick,
        onRetry = viewModel::loadNews,
        onFilterClick = { showFilters = true }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    state: DashboardState,
    onArticleClick: (NewsArticle) -> Unit,
    onMapClick: () -> Unit,
    onRetry: () -> Unit,
    onFilterClick: () -> Unit
) {
    if (state.error != null) {
        ErrorScreen(
            message = state.error,
            onRetry = onRetry
        )
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "OMNIAXIS",
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        TextButton(onClick = onMapClick) {
                            Text("MAP", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        IconButton(onClick = onFilterClick) {
                            Icon(Icons.Default.Tune, contentDescription = "Filters")
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.White)
            ) {
                HorizontalDivider(color = OmniAxisColors.DividerGray)
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "THE HORIZON FEED",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Text(
                        "LIVE SYNC",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }

                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = OmniAxisColors.PrimaryBlue)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        items(state.articles) { article ->
                            ArticleItem(article, onClick = { onArticleClick(article) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: NewsArticle, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Text(
            text = article.title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 38.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "News",
                fontSize = 11.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            Text(
                " • ",
                color = Color.Gray
            )
            Text(
                article.publishedAt.take(10),
                fontSize = 11.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                "LIVE",
                fontSize = 11.sp,
                color = OmniAxisColors.PrimaryBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    val mockArticles = listOf(
        NewsArticle(
            id = 1,
            publishedAt = "2024-03-20T09:00:00Z",
            title = "Silicon Valley Bank Crisis",
            description = "Immediate freeze on all regional tech sector accounts linked to primary SVB routing numbers.",
            body = "",
            language = "en",
            locations = emptyList(),
            jargonTerms = emptyList(),
            summary = emptyList(),
            storyId = 1
        )
    )
    OmniAxionTheme {
        DashboardContent(
            state = DashboardState(articles = mockArticles),
            onArticleClick = {},
            onMapClick = {},
            onRetry = {},
            onFilterClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardErrorPreview() {
    OmniAxionTheme {
        DashboardContent(
            state = DashboardState(error = "Unable to sync with the news grid."),
            onArticleClick = {},
            onMapClick = {},
            onRetry = {},
            onFilterClick = {}
        )
    }
}
