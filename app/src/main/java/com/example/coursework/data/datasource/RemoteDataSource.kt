package com.example.testmvvm.data.datasource

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteDataSource {
    private const val BASE_URL = "http://android-api.std-926.ist.mospolytech.ru/"

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}