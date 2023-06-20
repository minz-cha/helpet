package com.helpet.calendar

import com.helpet.vector.VectorService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CalRetrofitInterface {
        private const val BASE_URL = "http://172.30.1.8:3000/api/"

        val retrofit3: Retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //val calendarApiService = retrofit.create(VectorService::class.java)


}