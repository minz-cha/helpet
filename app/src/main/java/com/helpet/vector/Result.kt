package com.helpet.vector


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("petAge")
    val petAge: Int,
    @SerializedName("petBirth")
    val petBirth: String,
    @SerializedName("petGender")
    val petGender: String,
    @SerializedName("petIdx")
    val petIdx: Int,
    @SerializedName("petImg")
    val petImg: String,
    @SerializedName("petName")
    val petName: String,
    @SerializedName("petSpecies")
    val petSpecies: String,
    @SerializedName("userId")
    val userId: String
)