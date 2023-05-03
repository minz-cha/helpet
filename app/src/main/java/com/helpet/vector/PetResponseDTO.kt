package com.helpet.vector


import com.google.gson.annotations.SerializedName

data class PetResponseDTO(
    @SerializedName("status")
    val status: String
)