package com.az.channel.utils

import com.az.channel.model.BackgroundImage
import com.az.channel.model.ChannelItemNew
import com.az.channel.model.WeatherInfo
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface Endpoints {
    @GET("/channels")
    fun getChannelList(): Call<List<ChannelItemNew>>

    @GET("/getBackgroundImage")
    fun getBackgroundImage(): Call<BackgroundImage>

    @GET("/getWeatherInformation")
    fun getWeatherInformation(): Call<WeatherInfo>
 }