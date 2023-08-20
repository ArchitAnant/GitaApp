package com.timepass.gita.weather

import com.timepass.gita.weather.Current
import com.timepass.gita.weather.Location

data class WeatherData(
    val current: Current,
    val location: Location
)