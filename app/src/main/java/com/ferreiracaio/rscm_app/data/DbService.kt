package com.ferreiracaio.rscm_app.data

import com.ferreiracaio.rscm_app.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DbService {

    @POST("/users")
    fun createUser(@Body newUser: User): Call<User>
}