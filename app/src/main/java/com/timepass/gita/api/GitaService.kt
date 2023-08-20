package com.timepass.gita.api

import com.google.gson.GsonBuilder
import com.timepass.gita.Verse.GitaVerse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface GitaInterface{
    @GET("/slok/{chapter}/{verse}")
    fun getVerse(@Path("chapter") chapter:Int, @Path("verse") verse:Int) : Call<GitaVerse>
}

private var gson = GsonBuilder()
    .setLenient()
    .create()

object GitaService{
    val gitaInterface : GitaInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bhagavadgitaapi.in")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        gitaInterface = retrofit.create(GitaInterface::class.java)
    }
}