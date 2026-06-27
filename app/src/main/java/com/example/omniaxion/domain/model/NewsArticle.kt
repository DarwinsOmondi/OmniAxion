package com.example.omniaxion.domain.model

data class NewsArticle(
    val id: Long,
    val publishedAt: String,
    val title: String,
    val description: String,
    val body: String,
    val language: String,
    val locations: List<GeoPoint>,
    val jargonTerms: List<JargonTerm>,
    val summary: List<String>,
    val storyId: Long?
)
