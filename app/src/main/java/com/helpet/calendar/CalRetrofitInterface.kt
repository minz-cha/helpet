package com.helpet.calendar

import com.helpet.vector.VectorService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CalRetrofitInterface {
        private const val BASE_URL = "http://192.168.183.0:3000/api/"

        val retrofit3: Retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //val calendarApiService = retrofit.create(VectorService::class.java)


}