package com.az.channel.utils

import com.az.channel.model.BackgroundImage
import com.az.channel.model.ChannelItemNew
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface Endpoints {
    @GET("/getChannelList")
    fun getChannelList(): Call<List<ChannelItemNew>>

    @GET("/getBackgroundImage")
    fun getBackgroundImage(): Call<BackgroundImage>
 }