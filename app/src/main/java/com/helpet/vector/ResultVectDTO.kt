package com.helpet.vector

data class ResultVectDTO(
    val asymptomaticProbability: Double,
    val diseaseNames: List<String>,
    val symptomProbability: Double
)