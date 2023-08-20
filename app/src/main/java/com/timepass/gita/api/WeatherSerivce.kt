package com.timepass.gita.api

import com.timepass.gita.weather.WeatherData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WeatherInterface{
    @GET("current.json?key=83b16e1b4a1d4c9e9e694023211906&q=Kolkata")
    fun getWeather(): Call<WeatherData>
}
object WeatherService{
    val weatherInterface: WeatherInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherInterface = retrofit.create(WeatherInterface::class.java)
    }
}

