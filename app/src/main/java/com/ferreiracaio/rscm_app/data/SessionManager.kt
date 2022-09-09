package com.ferreiracaio.rscm_app.data

import android.content.Context
import android.content.SharedPreferences
import com.ferreiracaio.rscm_app.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), 0)

    companion object{
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
        editor.commit()
    }

    fun fetchAuthToken(): String?{
        return prefs.getString(USER_TOKEN, null)
    }

    fun removeToken(){
        prefs.edit().clear().commit()
    }


}