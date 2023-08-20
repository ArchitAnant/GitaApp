package com.timepass.gita.weather

import com.google.gson.annotations.SerializedName
import com.timepass.gita.weather.Condition

data class Current(
    val condition: Condition,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("precip_in")
    val precipIn: Double,
    @SerializedName("temp_c")
    val temp: Int,
    @SerializedName("wind_kph")
    val windph: Double,
)