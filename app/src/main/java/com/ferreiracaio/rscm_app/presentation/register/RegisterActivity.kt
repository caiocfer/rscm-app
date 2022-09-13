package com.ferreiracaio.rscm_app.presentation.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ferreiracaio.rscm_app.databinding.ActivityRegisterBinding
import com.ferreiracaio.rscm_app.models.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignIn.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val userName = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (name.isEmpty() or userName.isEmpty() or email.isEmpty() or password.isEmpty()){
                Toast.makeText(this,"Please fill all the fields.",Toast.LENGTH_SHORT).show()

            }else{
                val newUser = User(userName, name,email,password)
                viewModel.createNewUser(newUser)
                finish()
            }
        }
    }
}