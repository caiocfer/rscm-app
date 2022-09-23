package com.ferreiracaio.rscm_app.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreatePostRequest(
    val title: String,
    val content: String,
    @SerializedName("music_title") val musicTitle: String,
    @SerializedName("music_link") val musicLink: String
): Serializable