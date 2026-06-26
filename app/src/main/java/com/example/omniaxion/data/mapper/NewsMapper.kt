package com.example.omniaxion.data.mapper

import com.example.omniaxion.data.model.ArticleResponseDto
import com.example.omniaxion.data.model.EntityDto
import com.example.omniaxion.data.model.LocationDto
import com.example.omniaxion.domain.model.GeoPoint
import com.example.omniaxion.domain.model.JargonTerm
import com.example.omniaxion.domain.model.NewsArticle

fun ArticleResponseDto.toDomain(): NewsArticle {
    return NewsArticle(
        id = id,
        publishedAt = publishedAt,
        title = title,
        description = description,
        body = body,
        language = language,
        locations = locationsMentioned?.map { it.toDomain() } ?: emptyList(),
        jargonTerms = entities?.map { it.toDomain() } ?: emptyList(),
        summary = summary?.map { it.sentence } ?: emptyList(),
        storyId = story?.id
    )
}

fun LocationDto.toDomain(): GeoPoint {
    return GeoPoint(
        name = name,
        countryCode = countryCode,
        latitude = latitude,
        longitude = longitude,
        type = type
    )
}

fun EntityDto.toDomain(): JargonTerm {
    return JargonTerm(
        id = id,
        term = name,
        type = type,
        description = metadata?.description,
        occurrences = positionWrapper?.positions?.map { IntRange(it.start, it.end) } ?: emptyList()
    )
}
