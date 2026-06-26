package com.example.omniaxion.data.model

import com.google.gson.annotations.SerializedName

data class ArticleResponseDto(
    @SerializedName("id") val id: Long,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("body") val body: String,
    @SerializedName("language") val language: String,
    @SerializedName("locations_mentioned") val locationsMentioned: List<LocationDto>?,
    @SerializedName("entities") val entities: List<EntityDto>?,
    @SerializedName("summary") val summary: List<SummarySentenceDto>?,
    @SerializedName("story") val story: StoryMetadataDto?
)

data class LocationDto(
    @SerializedName("name") val name: String,
    @SerializedName("country") val countryCode: String,
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double,
    @SerializedName("type") val type: String
)

data class SummarySentenceDto(
    @SerializedName("sentence") val sentence: String
)

data class StoryMetadataDto(
    @SerializedName("id") val id: Long
)
