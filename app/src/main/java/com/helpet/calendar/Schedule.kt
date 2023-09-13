package com.helpet.calendar

import java.io.Serializable

data class Schedule(
    val startDate : String?,
    val endDate: String?,
    val title: String,
    val description: String,
    val cal_idx : Int
    ) : Serializable