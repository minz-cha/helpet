package com.helpet.vector



import com.google.gson.annotations.SerializedName

data class requestDto(

    @SerializedName("date")
    val date: Int,
    @SerializedName("petname")
    val petname: String,
    @SerializedName("username")
    val username: String
)


