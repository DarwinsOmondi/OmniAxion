package com.example.omniaxion.data.model

import com.google.gson.annotations.SerializedName

data class ApitubeResponseDto<T>(
    @SerializedName("total") val total: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("last_page") val lastPage: Int,
    @SerializedName("results") val results: List<T>
)
