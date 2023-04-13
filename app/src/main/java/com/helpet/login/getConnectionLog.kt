package com.helpet.login


import com.google.gson.annotations.SerializedName

data class getConnectionLog(
    val result : List<result>
)

data class result(
    @SerializedName("error")
    val errorcode: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("result")
    val result: String
)