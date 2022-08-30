package com.ferreiracaio.rscm_app.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ferreiracaio.rscm_app.data.ApiService
import com.ferreiracaio.rscm_app.data.SessionManager
import com.ferreiracaio.rscm_app.models.LoginUser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel() : ViewModel(){

    fun loginUser(email: String,password: String, context:Context){

        ApiService.service.loginUser(LoginUser(email,password)).enqueue(object :
            Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200){
                    val token = response.body()?.string().toString()
                    saveToken(token, context)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun saveToken(token: String,context: Context){
        val sessionManager = SessionManager(context)
        sessionManager.saveAuthToken(token)
    }
}