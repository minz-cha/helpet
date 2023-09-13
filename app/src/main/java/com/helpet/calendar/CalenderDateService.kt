package com.helpet.calendar

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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
        @Field("startdate") startdate: String?,
        @Field("enddate") enddate: String?,
        @Field("userId") userId: String,
        @Field("title") title: String,
        @Field("description") description: String
    ): Call<CalendarPlanResultDTO?>
}


//캘린더 수정
interface CalendarEditService{
    @FormUrlEncoded
    @PUT("calendar/update")
    fun CalendarUpdate(
        @Field("cal_idx") cal_idx : Int?,
        @Field("userId") userId: String,
        @Field("startdate") startdate: String?,
        @Field("enddate") enddate: String?,
        @Field("title") title: String,
        @Field("description") description: String
    ) :Call<UpdateScheduleDTO>
}
//일정 삭제하기
interface CalendarDeleteService{
    @DELETE("calendar/delete/{cal_idx}")
    fun calendarDelete(@Path("cal_idx") cal_idx: Int?): Call<DeleteScheduleDTO>
}




