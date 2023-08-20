package com.timepass.gita.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timepass.gita.Utils.demoGita
import com.timepass.gita.Verse.GitaVerse
import com.timepass.gita.api.GitaService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitaViewModel:ViewModel() {
    var _gitaObject = MutableStateFlow(demoGita)
    var gitaObject = _gitaObject.asStateFlow()
    fun getVerse(chapter:Int,verse:Int) {
        viewModelScope.launch {
            val verseItem = GitaService.gitaInterface.getVerse(chapter = chapter, verse = verse)
            verseItem.enqueue(object : Callback<GitaVerse> {
                override fun onResponse(call: Call<GitaVerse>, response: Response<GitaVerse>) {
                    val verseDta = response.body()
                    if (verseDta != null) {
                        _gitaObject.value = verseDta
                    }
                }
                override fun onFailure(call: Call<GitaVerse>, t: Throwable) {
                    getVerse(chapter,verse)
                }
            })
        }
    }
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
         setGitaMap()
     }
}