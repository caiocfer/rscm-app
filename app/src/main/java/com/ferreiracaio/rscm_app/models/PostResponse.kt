package com.ferreiracaio.rscm_app.models

import com.google.gson.annotations.SerializedName

data class PostResponse(
    val author_id: Int,
    val author_username: String,
    val content: String,
    val post_id: Int,
    val title: String,
    @SerializedName("music_title") val musicTitle: String,
    @SerializedName("music_link") val musicLink: String
)