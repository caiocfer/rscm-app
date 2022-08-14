package com.ferreiracaio.rscm_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.ferreiracaio.rscm_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            Toast.makeText(this,"Login",Toast.LENGTH_SHORT).show()
        }

        binding.buttonSignIn.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}