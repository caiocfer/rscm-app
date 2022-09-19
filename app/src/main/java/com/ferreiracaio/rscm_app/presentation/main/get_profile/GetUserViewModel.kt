package com.ferreiracaio.rscm_app.presentation.main.get_profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferreiracaio.rscm_app.data.ApiService
import com.ferreiracaio.rscm_app.data.SessionManager
import com.ferreiracaio.rscm_app.models.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetUserViewModel: ViewModel() {

    val postsLiveData: MutableLiveData<List<PostResponse>> = MutableLiveData()
    val postList = ArrayList<PostResponse>()

    fun getUserPosts(context: Context, authorId: Int){
        postList.clear()

        val session = SessionManager(context)
        val token = "Bearer ${session.fetchAuthToken().toString()}"

        ApiService.service.getUserPosts(token, authorId).enqueue(object: Callback<List<PostResponse>>{
            override fun onResponse(
                call: Call<List<PostResponse>>,
                response: Response<List<PostResponse>>
            ) {
                when{
                    response.isSuccessful -> {
                        response.body()?.let {
                            for(results in response.body()!!){
                                postList.add(results)
                            }
                        }
                        postsLiveData.value = postList
                        Log.d("TAG", "onResponse: ${postsLiveData.value}")

                    }
                }
            }

            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}