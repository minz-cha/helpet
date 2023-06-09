package com.helpet.calendar

import java.io.Serializable

data class Schedule(
    val calIdx: String,
//    val userId: String,
    val date: String?,
    val title: String,
//    val month: String,
    val description: String
    ) : Serializable