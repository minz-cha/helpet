package com.helpet.vector


import com.google.gson.annotations.SerializedName

data class petListResponseDTO(
    @SerializedName("status")
    val status: String,
    @SerializedName("result")
    val result: List<Result>
)