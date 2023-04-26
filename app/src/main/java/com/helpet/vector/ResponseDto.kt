package com.helpet.vector

import com.google.gson.annotations.SerializedName

//data class ResponseDto(
//    @SerializedName("result")
//    val result: Result? = null
//){
//    data class Result(
//        @SerializedName("name")
//        val name: String,
//        @SerializedName("asymptomaticProbability")
//        val asymptomaticProbability: Double,
//        @SerializedName("symptomProbability")
//        val symptomProbability: Double
//    )
//}

//data class ResponseDto(
//    val result: List<result>
//)

data class ResponseDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("asymptomaticProbability")
    val asymptomaticProbability: Double,
    @SerializedName("symptomProbability")
    val symptomProbability: Double
)

//data class result(
//    @SerializedName("name")
//    val name: String,
//    @SerializedName("asymptomaticProbability")
//    val asymptomaticProbability: Double,
//    @SerializedName("symptomProbability")
//    val symptomProbability: Double
//)
