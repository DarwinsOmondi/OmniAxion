package com.example.omniaxion.data.model

import com.google.gson.annotations.SerializedName

data class EntityDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("body") val positionWrapper: EntityPositionWrapperDto?,
    @SerializedName("metadata") val metadata: EntityMetadataDto?
)

data class EntityPositionWrapperDto(
    @SerializedName("pos") val positions: List<TextRangeDto>?
)

data class TextRangeDto(
    @SerializedName("start") val start: Int,
    @SerializedName("end") val end: Int
)

data class EntityMetadataDto(
    @SerializedName("description") val description: String?
)
