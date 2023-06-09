package com.helpet.calendar

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

//처음 접속 후 월별 일정 가져오기
interface CalendarDateService {
    @FormUrlEncoded
    @POST("calendar/")
    fun getMonthlySchedule(
        @Field("userId") userId: String
    ): Call<MonthlyScheduleDTO>
}

//캘린더 월별 가져오기
interface CalendarMonthService{
    @FormUrlEncoded
    @POST("calendar/month")
    fun selMonthSchedule(
        @Field("userId") userId: String,
        @Field("month") month:Int
    ) :Call<MonthlyScheduleDTO>

}

//캘린더 등록
interface CalendarService {
    @FormUrlEncoded
    @POST("calendar/add")
    fun CalendarResult(
        @Field("date") date: String?,
        @Field("userId") userId: String,
        @Field("title") title: String,
        @Field("description") description: String
    ): Call<CalendarPlanResultDTO?>
}


