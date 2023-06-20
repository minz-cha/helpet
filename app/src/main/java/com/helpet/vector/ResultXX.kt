package com.helpet.vector


import com.google.gson.annotations.SerializedName

data class ResultXX(
    @SerializedName("vectImg")
    val vectImg: String,
    @SerializedName("vectDate")
    val vectDate: String,
    @SerializedName("vectName")
    val vectName: String,
    @SerializedName("vectProb")
    val vectProb: Double
)