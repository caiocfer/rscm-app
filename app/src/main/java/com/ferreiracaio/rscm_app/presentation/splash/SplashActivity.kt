package com.ferreiracaio.rscm_app.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.ferreiracaio.rscm_app.AccessActivity
import com.ferreiracaio.rscm_app.data.SessionManager
import com.ferreiracaio.rscm_app.databinding.ActivitySplashBinding
import com.ferreiracaio.rscm_app.presentation.main.MainActivity

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
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            startActivity(Intent(this, AccessActivity::class.java))
        }
    }

}