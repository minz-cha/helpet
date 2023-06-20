package com.helpet.vector

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class petImgResponseDTO(
    @SerializedName("img")
    val img:MultipartBody.Part
)
