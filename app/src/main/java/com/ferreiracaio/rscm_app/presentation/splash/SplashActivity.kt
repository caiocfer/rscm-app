package com.ferreiracaio.rscm_app.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.ferreiracaio.rscm_app.AccessActivity
import com.ferreiracaio.rscm_app.data.SessionManager
import com.ferreiracaio.rscm_app.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = SessionManager(this)
        val token = session.fetchAuthToken()

        if (token != null){
            Toast.makeText(this, "Welcome back",Toast.LENGTH_SHORT).show()
        }else{
            startActivity(Intent(this, AccessActivity::class.java))
            Toast.makeText(this, "Please login",Toast.LENGTH_SHORT).show()
        }
    }

}