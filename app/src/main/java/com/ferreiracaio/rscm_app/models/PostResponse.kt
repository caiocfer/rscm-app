package com.ferreiracaio.rscm_app.models

data class PostResponse(
    val author_id: Int,
    val author_username: String,
    val content: String,
    val post_id: Int,
    val title: String
)