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
    @SerializedName("startdate")
    val startdate: String,
    @SerializedName("enddate")
    val enddate: String,
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

//월별 수정 DTO
data class UpdateScheduleDTO(
    @SerializedName("status")
    val status: Boolean,
//    @SerializedName("title")
//    val title : String,
//    @SerializedName("description")
//    val description : String
)

//월별 삭제 DTO
data class DeleteScheduleDTO(
    @SerializedName("status")
    val status: String,
    @SerializedName("cal_idx")
    val cal_idx : String,
    @SerializedName("message")
    val message : String
)

//data class MonthlyScheduleresponseDTO(
//    @SerializedName("message")
//    val message: String,
//    @SerializedName("success")
//    val success: Boolean,
//    @SerializedName("nickname")
//    val nickname: String
//)



