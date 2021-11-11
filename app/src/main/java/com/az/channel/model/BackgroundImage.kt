package com.az.channel.model

import com.google.gson.annotations.SerializedName

data class BackgroundImage(
    @SerializedName("image")
    val imageUrl: String
)