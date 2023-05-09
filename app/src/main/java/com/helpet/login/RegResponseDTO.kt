package com.helpet.login

import com.google.gson.annotations.SerializedName

data class RegResponseDTO(
    @SerializedName("message")
    val message: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("userId")
    val userId: String
)

data class LogResponseDTO(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("userId")
    val userId: String
)

data class IdCheckResult(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("userId")
    val userId: String
)

data class NickCheckResult(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("nickname")
    val nickname: String
)

