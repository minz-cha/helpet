package com.helpet.calendar

import com.google.gson.annotations.SerializedName

//일정 추가 시, 저장 요청
data class CalendarPlanResultDTO(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String
)

//개별 일정 정보
data class CalendarPlanDTO(
    @SerializedName("cal_idx")
    val cal_idx: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String
)

//월별 일정 정보 DTO
data class MonthlyScheduleDTO(
    @SerializedName("status")
    val status: String,
    @SerializedName("month")
    val month: Int,
    @SerializedName("userId")
    val userId : String,
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



