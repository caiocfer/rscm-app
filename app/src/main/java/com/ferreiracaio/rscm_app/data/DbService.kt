package com.ferreiracaio.rscm_app.data

import com.ferreiracaio.rscm_app.models.LoginUser
import com.ferreiracaio.rscm_app.models.PostResponse
import com.ferreiracaio.rscm_app.models.User
import com.ferreiracaio.rscm_app.models.UserRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DbService {

    @POST("/users")
    fun createUser(@Body newUser: User): Call<User>

    @POST("/login")
    fun loginUser(@Body loginUser: LoginUser): Call<ResponseBody>

    @GET("/user")
    fun getUserProfile(@Header("Authorization") token:String?):Call<UserRequest>

    @GET("/search?")
    fun searchUsers(@Header("Authorization") token:String?, @Query("user") query:String): Call<List<UserRequest>>

    @GET("/posts/{userId}")
    fun getUserPosts(@Header("Authorization") token: String?, @Path("userId") userID: Int): Call<List<PostResponse>>



}