package com.az.channel.utils

import com.az.channel.model.ChannelItemNew
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface Endpoints {
    @GET("iptv.json")
    fun getChannelList(): Call<List<ChannelItemNew>>

    @GET("images.json")
    fun getBackgroundImage(): Call<JsonObject>
 }