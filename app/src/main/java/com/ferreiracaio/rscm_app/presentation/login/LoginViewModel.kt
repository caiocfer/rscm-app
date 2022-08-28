package com.ferreiracaio.rscm_app.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ferreiracaio.rscm_app.data.ApiService
import com.ferreiracaio.rscm_app.models.LoginUser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel(){

    fun loginUser(email: String,password: String){

        ApiService.service.loginUser(LoginUser(email,password)).enqueue(object :
            Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200){
                    Log.d("TAG", "onResponse: ${response.body()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }


}