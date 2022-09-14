package com.ferreiracaio.rscm_app.presentation.main.get_profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.ferreiracaio.rscm_app.R
import com.ferreiracaio.rscm_app.databinding.ActivityGetUserBinding
import com.ferreiracaio.rscm_app.models.User

class GetUserActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityGetUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityGetUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.extras?.get("USER_DETAILS") as User
        fillUserFields(user)


    }

    private fun fillUserFields(user:User){
        binding.textViewUsername.text = user.username
        binding.textViewName.text = user.name
        binding.textViewEmail.text = user.email
    }
}