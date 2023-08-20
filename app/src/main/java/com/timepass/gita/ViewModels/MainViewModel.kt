package com.timepass.gita.ViewModels

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timepass.gita.Verse.GitaVerse
import com.timepass.gita.api.GitaService
import com.timepass.gita.api.WeatherService
import com.timepass.gita.Utils.demoGita
import com.timepass.gita.Utils.demoWeather
import com.timepass.gita.weather.WeatherData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainViewModel: ViewModel() {

    private var _weatherDataN = MutableStateFlow(
        demoWeather
    )
    var _gitaDataN = MutableStateFlow(demoGita)
    var weatherData = _weatherDataN.asStateFlow()
    var gitaData = _gitaDataN.asStateFlow()

    private var chap = 1
    private var ver = 1
    val map:HashMap<Int,Int>  = HashMap()

    private fun setGitaMap(){
        map[1] = 47
        map[2] = 72
        map[3] = 43
        map[4] = 42
        map[5] = 29
        map[6] = 47
        map[7] = 30
        map[8] = 28
        map[9] = 34
        map[10] = 42
        map[11] = 55
        map[12] = 20
        map[13] = 35
        map[14] = 27
        map[15] = 20
        map[16] = 24
        map[17] = 28
        map[18] = 78
    }

    init {
        getWeather()
        getVerse(chapter = chap, verse = ver)
        setGitaMap()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getWeather(){
        viewModelScope.launch {
        val weather = WeatherService.weatherInterface.getWeather()
            weather.enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    val weatherDta = response.body()
                    if (weatherDta != null) {
                        _weatherDataN.value = weatherDta
                    }
                }
                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    getWeather()
                }
              }
            )
        }
    }

    private fun getVerse(chapter:Int,verse:Int) {
        viewModelScope.launch {
            val verseItem = GitaService.gitaInterface.getVerse(chapter = chapter, verse = verse)//chapter = 1, verse = 1)
            verseItem.enqueue(object : Callback<GitaVerse> {
                override fun onResponse(call: Call<GitaVerse>, response: Response<GitaVerse>) {
                    val verseDta = response.body()
                    if (verseDta != null) {
                        _gitaDataN.value = verseDta
                    }
                }
                override fun onFailure(call: Call<GitaVerse>, t: Throwable) {
                    getVerse(chapter,verse)
                }
            })
        }
    }



    fun onForwardClick(chapter: Int,verse: Int){
        if(verse<= map[chapter]!!){
            getVerse(chapter = chapter, verse = verse+1)
        }
        else if(verse>map[chapter]!!){
            chap+=1
            ver=1
        }
    }
    fun OnBackClick(chapter:Int,verse:Int){
        if(map[chapter]!! >1){
            getVerse(chapter = chapter, verse = verse-1)
        }
        else if(verse==1 && chapter!=1){
            getVerse(chapter = chapter-1, verse = map[chapter-1]!!)
        }
    }
}