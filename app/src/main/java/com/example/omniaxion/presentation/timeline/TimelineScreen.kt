package com.example.omniaxion.presentation.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omniaxion.domain.model.NewsArticle
import com.example.omniaxion.ui.theme.OmniAxionTheme
import com.example.omniaxion.ui.theme.OmniAxisColors

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    title: String,
    articles: List<NewsArticle>,
    onBackClick: () -> Unit,
    onMapClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "OMNIAXIS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 2.sp
                        )
                        Text(
                            "Timeline View",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("\u2190", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onClick = onMapClick) {
                        Icon(Icons.Default.Public, contentDescription = "Map")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
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
            
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = title,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 38.sp,
                    color = OmniAxisColors.PrimaryBlue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    TextButton(onClick = {}) {
                        Text("\u2191 EXPORT", color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp)
            ) {
                itemsIndexed(articles) { index, article ->
                    TimelineItem(article, isLast = index == articles.lastIndex)
                }
            }
        }
    }
}

@Composable
fun TimelineItem(article: NewsArticle, isLast: Boolean) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color.Black, CircleShape)
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .weight(1f)
                        .background(Color.Black)
                )
            }
        }
        
        Column(modifier = Modifier.padding(start = 16.dp, bottom = 48.dp)) {
            Text(
                "09:00 AM",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = OmniAxisColors.PrimaryBlue
            )
            Text(
                text = article.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Primary Source: FDIC Press Release",
                fontSize = 11.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(OmniAxisColors.SubtleGray)
                    .padding(12.dp)
            ) {
                Text("IMPACT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Text(
                    text = article.description,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimelineScreenPreview() {
    val mockArticles = listOf(
        NewsArticle(
            id = 1,
            publishedAt = "09:00 AM",
            title = "Regulators shut down SVB",
            description = "Immediate freeze on all regional tech sector accounts linked to primary SVB routing numbers.",
            body = "",
            language = "en",
            locations = emptyList(),
            jargonTerms = emptyList(),
            summary = emptyList(),
            storyId = 1
        ),
        NewsArticle(
            id = 2,
            publishedAt = "01:00 PM",
            title = "Tech founders sound panic alarms",
            description = "Analysis: 12 regional reports indicate widespread liquidity concerns.",
            body = "",
            language = "en",
            locations = emptyList(),
            jargonTerms = emptyList(),
            summary = emptyList(),
            storyId = 1
        )
    )
    OmniAxionTheme {
        TimelineScreen(
            title = "Silicon Valley Bank Crisis",
            articles = mockArticles,
            onBackClick = {},
            onMapClick = {}
        )
    }
}
