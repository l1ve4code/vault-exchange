package com.example.testmvvm.data.converter

import androidx.room.TypeConverter
import com.example.testmvvm.data.model.Rate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RateConverter {
    @TypeConverter
    fun fromRate(rates: List<Rate>): String{
        return Gson().toJson(rates)
    }

    @TypeConverter
    fun toRate(rates: String): List<Rate>{
        val listType = object : TypeToken<List<Rate>>() {}.type
        return Gson().fromJson(rates, listType)
    }
}