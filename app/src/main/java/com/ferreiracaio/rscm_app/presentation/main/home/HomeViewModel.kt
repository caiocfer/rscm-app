package com.ferreiracaio.rscm_app.presentation.main.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferreiracaio.rscm_app.data.ApiService
import com.ferreiracaio.rscm_app.data.SessionManager
import com.ferreiracaio.rscm_app.models.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {

    val postsLiveData: MutableLiveData<List<PostResponse>> = MutableLiveData()
    val postList = ArrayList<PostResponse>()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getFeed(context: Context){
        isLoading.value = true
        postList.clear()

        val session = SessionManager(context)
        val token = "Bearer ${session.fetchAuthToken().toString()}"

        ApiService.service.getFeed(token).enqueue(object : Callback<List<PostResponse>>{
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
            }

            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                Toast.makeText(context, "Failed to get feed, please try again!", Toast.LENGTH_SHORT).show()
                isLoading.value = false
            }


        })


    }

}