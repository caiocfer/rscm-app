package com.ferreiracaio.rscm_app.presentation.main.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferreiracaio.rscm_app.data.ApiService
import com.ferreiracaio.rscm_app.data.SessionManager
import com.ferreiracaio.rscm_app.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    val userLiveData:MutableLiveData<User> = MutableLiveData<User>()

    fun getUserData(context:Context){
        val session = SessionManager(context)
        val token = "Bearer ${session.fetchAuthToken().toString()}"
        ApiService.service.getUserProfile(token).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    userLiveData.value = response.body()
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun signOutUser(context: Context){
        val sessionManager = SessionManager(context)
        sessionManager.removeToken()
    }


}