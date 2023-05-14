package com.helpet.vector


import com.google.gson.annotations.SerializedName

data class petListResponseDTO(
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String
)