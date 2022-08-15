package com.ferreiracaio.rscm_app.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ferreiracaio.rscm_app.data.ApiService
import com.ferreiracaio.rscm_app.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel :ViewModel(){

    fun createNewUser(user: User){

        ApiService.service.createUser(newUser = user).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    Log.e("RegisterViewModel", "onResponse: Created User!")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("RegisterViewModel", "onFailure: Failed to create user $t", )
            }

        })
    }
}