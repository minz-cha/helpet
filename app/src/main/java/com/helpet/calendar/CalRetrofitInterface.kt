package com.helpet.calendar

import com.helpet.vector.VectorService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CalRetrofitInterface {
        private const val BASE_URL = "http://10.0.2.2:3000/api/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //val calendarApiService = retrofit.create(VectorService::class.java)


}