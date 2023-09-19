package com.helpet.login

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInterface {

//    private const val BASE_URL = "http://10.0.2.2:3000/api/"
    private const val BASE_URL = "http://172.30.1.24:3000/api/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}