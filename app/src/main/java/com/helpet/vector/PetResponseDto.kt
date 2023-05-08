package com.helpet.vector


import com.google.gson.annotations.SerializedName

data class PetResponseDto(
    @SerializedName("petList")
    val petList: List<Pet>,
    @SerializedName("status")
    val status: String
)