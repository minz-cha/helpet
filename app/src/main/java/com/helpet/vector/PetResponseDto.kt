package com.helpet.vector


import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class PetResponseDto(
    @SerializedName("status")
    val status: String
//    @SerializedName("userId")
//    val userId: String,
//    @SerializedName("petImg")
//    val petImg: String,
//    @SerializedName("petSpecies")
//    val petSpecies: String,
//    @SerializedName("petName")
//    val petName: String,
//    @SerializedName("petAge")
//    val petAge: Int,
//    @SerializedName("petBirth")
//    val petBirth: String,
//    @SerializedName("petGender")
//    val petGender: String

)