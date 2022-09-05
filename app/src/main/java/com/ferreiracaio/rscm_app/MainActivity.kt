package com.ferreiracaio.rscm_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.ferreiracaio.rscm_app.databinding.ActivityMainBinding
import com.ferreiracaio.rscm_app.presentation.login.LoginActivity
import com.ferreiracaio.rscm_app.presentation.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.buttonSignIn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}