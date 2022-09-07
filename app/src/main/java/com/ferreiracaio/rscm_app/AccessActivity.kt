package com.ferreiracaio.rscm_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.ferreiracaio.rscm_app.databinding.ActivityAccessBinding
import com.ferreiracaio.rscm_app.presentation.login.LoginActivity
import com.ferreiracaio.rscm_app.presentation.register.RegisterActivity

class AccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.buttonSignIn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}