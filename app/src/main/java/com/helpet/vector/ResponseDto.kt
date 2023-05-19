package com.helpet.vector

import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("asymptomaticProbability")
    val asymptomaticProbability: Double,
    @SerializedName("symptomProbability")
    val symptomProbability: Double,
    @SerializedName("vectContent")
    val vectContent : String,
)
