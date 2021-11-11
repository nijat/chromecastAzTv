package com.az.channel.model

import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    @SerializedName("temperature")
    val temperature: String,

    @SerializedName("wind")
    val wind: String
)