package com.helpet.vector


import com.google.gson.annotations.SerializedName

data class Pet(
    @SerializedName("petAge")
    val petAge: Int,
    @SerializedName("petBirth")
    val petBirth: String,
    @SerializedName("petSpecies")
    val petSpecies: String,
    @SerializedName("petGender")
    val petGender: String,
    @SerializedName("petImg")
    val petImg: String,
    @SerializedName("petName")
    val petName: String
)