package com.az.channel.model

import com.google.gson.annotations.SerializedName

data class ChannelItemNew(
    @SerializedName("name")
    val name: String,
    @SerializedName("logo_url")
    val logoUrl: String,
    @SerializedName("stream_url")
    val streamUrl: String
)