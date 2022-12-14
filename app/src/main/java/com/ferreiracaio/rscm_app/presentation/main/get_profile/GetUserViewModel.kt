package com.ferreiracaio.rscm_app.presentation.main.get_profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferreiracaio.rscm_app.data.ApiService
import com.ferreiracaio.rscm_app.data.SessionManager
import com.ferreiracaio.rscm_app.models.PostResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetUserViewModel: ViewModel() {

    val postsLiveData: MutableLiveData<List<PostResponse>> = MutableLiveData()
    val postList = ArrayList<PostResponse>()

    val isFollowing: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getUserPosts(context: Context, authorId: Int){
        postList.clear()
        isLoading.value = true
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
                        Log.d("TAG", "onResponse: ${postsLiveData}")
                        isLoading.value = false

                    }
                }
                Log.d("TAG", "onResponse: ${postsLiveData.value}")
                Log.d("TAG", "onResponse: ${postList}")
                Log.d("TAG", "onResponse: ${response.code()}")

            }
            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                isLoading.value = false
            }

        })
    }

    fun getFollowing(context: Context, userId: Int){
        val session = SessionManager(context)
        val token = "Bearer ${session.fetchAuthToken().toString()}"

        ApiService.service.getFollowing(token, userId).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("TAG", "onResponse: Checking user follow")
                when{
                    response.code() == 200 ->{
                        isFollowing.value = true
                        Log.d("TAG", "observeIsFollowing: is following")

                    }
                    response.code() == 204 ->{
                        isFollowing.value = false
                        Log.d("TAG", "observeIsFollowing: is not following")

                    }
                }

                Log.e("TAG", "observeIsFollowing: ${response.message()}", )
                Log.d("TAG", "observeIsFollowing: ${response.code()}", )
                Log.d("TAG", "observeIsFollowing: ${call.request()}", )
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("TAG", "onFailure: Failed loading following", )
            }


        })

    }

    fun followUser(context:Context, userId: Int){
        val session = SessionManager(context)
        val token = "Bearer ${session.fetchAuthToken().toString()}"

        ApiService.service.followUser(token, userId).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
               when{
                   response.isSuccessful ->{
                       Log.d("TAG", "onResponse: ${response.code()}")
                       isFollowing.value = true
                   }
               }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }

        })

    }

    fun unfollowUser(context:Context, userId: Int) {
        val session = SessionManager(context)
        val token = "Bearer ${session.fetchAuthToken().toString()}"
        ApiService.service.unfollowUser(token,userId).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when{
                    response.isSuccessful ->{
                        Log.d("TAG", "onResponse: ${response.code()}")
                        isFollowing.value = false
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }


        })
    }

}