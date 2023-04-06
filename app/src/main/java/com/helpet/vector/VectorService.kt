package com.helpet.vector



import android.content.IntentFilter.create
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.net.URI.create

interface VectorService {
    @Multipart
    @POST("/api/diagnosis/eye")
    fun vectorResult(
        @Part postImg: MultipartBody.Part,
//        @Part("date") date: RequestBody,
//        @Part("petname") petname: RequestBody,
//        @Part("username") username: RequestBody

    ): Call<ResponseDto>
}
