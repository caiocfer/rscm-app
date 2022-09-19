package com.ferreiracaio.rscm_app.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserRequest (
    @SerializedName("user_id")val userId: Int,
    val username: String,
    val name: String,
    val email: String,
    ):Serializable