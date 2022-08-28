package com.ferreiracaio.rscm_app.data

import com.ferreiracaio.rscm_app.models.LoginUser
import com.ferreiracaio.rscm_app.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface DbService {

    @POST("/users")
    fun createUser(@Body newUser: User): Call<User>

    @POST("/login")
    fun loginUser(@Body loginUser: LoginUser): Call<ResponseBody>


}