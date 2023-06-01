package com.helpet.calendar

import com.google.gson.annotations.SerializedName

//일정 추가 시, 저장 요청
data class CalendarPlanResultDTO(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)

//개별 일정 정보
data class CalendarPlanDTO(
    @SerializedName("cal_idx")
    val calIdx: Int,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("month")
    val month: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String
)

//월별 일정 정보 DTO
data class MonthlyScheduleDTO(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val result: List<CalendarPlanDTO>
)

//data class MonthlyScheduleresponseDTO(
//    @SerializedName("message")
//    val message: String,
//    @SerializedName("success")
//    val success: Boolean,
//    @SerializedName("nickname")
//    val nickname: String
//)



