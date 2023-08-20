package com.timepass.gita.Utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.timepass.gita.R
import com.timepass.gita.Verse.GitaVerse
import com.timepass.gita.Verse.Rams
import com.timepass.gita.weather.Condition
import com.timepass.gita.weather.Current
import com.timepass.gita.weather.Location
import com.timepass.gita.weather.WeatherData

val englishFont  = FontFamily(
    Font(R.font.encodesanssemiexpandedregular, weight = FontWeight.Normal),
    Font(R.font.encodesanssemiexpandedmedium, weight = FontWeight.Medium)
)

val hindiFont = FontFamily(
    Font(R.font.anekdevanagarivariablefont)
)

val backgroundGradient = listOf(Color(0xFF19245E), Color(0xFF542F4A))

val pallet = listOf(Color(0x5E53438A),Color(0xBD613C62))

val weatherPalletTextColor = Color(0xFFB9B2FF)

val gitaPalletColor = Color(0xFFD7ABD2)

val demoWeather: WeatherData = WeatherData(
    current = Current((Condition(text = "", icon = "")),
    humidity = 0,
    isDay = 0,
    precipIn = 0.0,
    temp = 0,
    windph = 0.0
    ),
    location = Location(country = "No Location",
    localtime = "",
    name = "",
    region = ""
    )
)
val expandedVerseColor = Color(0xFFA66292)

val demoGita: GitaVerse = GitaVerse(chapter = 1, verse = 1, slok = "No slok", rams = Rams("","",""))
val demoExpandedGita: GitaVerse = GitaVerse(chapter = 1, verse = 1, slok = "No slok", rams = Rams("","",""))

val expandedGitaVerseColor  =  Color(0xFF693C5C)