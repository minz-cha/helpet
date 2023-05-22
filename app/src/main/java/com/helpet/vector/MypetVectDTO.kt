package com.helpet.vector


import com.google.gson.annotations.SerializedName

data class MypetVectDTO(
    @SerializedName("petAge")
    val petAge: Int,
    @SerializedName("petBirth")
    val petBirth: String,
    @SerializedName("petName")
    val petName: String,
    @SerializedName("result")
    val result: List<ResultXX>,
    @SerializedName("status")
    val status: String
)