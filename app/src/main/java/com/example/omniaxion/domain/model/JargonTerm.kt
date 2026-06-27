package com.example.omniaxion.domain.model

data class JargonTerm(
    val id: Long,
    val term: String,
    val type: String,
    val description: String?,
    val occurrences: List<IntRange>
)
