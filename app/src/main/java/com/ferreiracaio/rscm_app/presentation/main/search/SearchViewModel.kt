package com.ferreiracaio.rscm_app.presentation.main.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferreiracaio.rscm_app.data.ApiService
import com.ferreiracaio.rscm_app.data.SessionManager
import com.ferreiracaio.rscm_app.models.User
import com.ferreiracaio.rscm_app.models.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel:ViewModel() {

    val usersLiveData: MutableLiveData<List<UserRequest>> = MutableLiveData()

    val userList = ArrayList<UserRequest>()

    fun searchUsers(context:Context,query:String){
        userList.clear()
        val session = SessionManager(context)
        val token = "Bearer ${session.fetchAuthToken().toString()}"
        ApiService.service.searchUsers(token,query).enqueue(object: Callback<List<UserRequest>>{
            override fun onResponse(call: Call<List<UserRequest>>, response: Response<List<UserRequest>>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            for(results in response.body()!!){
                                userList.add(results)
                            }
                        }
                        usersLiveData.value = userList
                        Log.d("TAG", "onResponse: Search ${usersLiveData.value}")
                    }else ->{
                    Log.d("TAG", "onResponse: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<UserRequest>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}