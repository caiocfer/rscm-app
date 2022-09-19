package com.ferreiracaio.rscm_app.presentation.main.create_post

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.ferreiracaio.rscm_app.data.ApiService
import com.ferreiracaio.rscm_app.data.SessionManager
import com.ferreiracaio.rscm_app.models.CreatePostRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePostViewModel:ViewModel() {

    fun createPost(context: Context, post: CreatePostRequest){

        val session = SessionManager(context)
        val token = "Bearer ${session.fetchAuthToken().toString()}"

        ApiService.service.createPost(token,post).enqueue(object: Callback<CreatePostRequest>{
            override fun onResponse(
                call: Call<CreatePostRequest>,
                response: Response<CreatePostRequest>
            ) {
                when{
                    response.isSuccessful ->{
                        Log.d("CreatePostViewModel", "onResponse: $response")
                    }

                }
            }

            override fun onFailure(call: Call<CreatePostRequest>, t: Throwable) {
                Log.e("CreatePostViewModel", "onFailure: Failed to post $t", )
            }

        })
    }




}