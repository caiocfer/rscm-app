package com.ferreiracaio.rscm_app.models

import java.io.Serializable


data class User(
    val username: String,
    val name: String,
    val email: String,
    val password: String
): Serializable