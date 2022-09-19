package com.ferreiracaio.rscm_app.presentation.main.create_post

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ferreiracaio.rscm_app.databinding.ActivityCreatePostBinding
import com.ferreiracaio.rscm_app.models.CreatePostRequest

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePostBinding
    private lateinit var viewModel: CreatePostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CreatePostViewModel::class.java)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createPostButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()

            if (checkFields(title,content)){
                val post = CreatePostRequest(title,content)
                viewModel.createPost(this,post)
                finish()
            }else{
                Toast.makeText(this,"Fields can't be empty",Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun checkFields(title: String, content:String):Boolean{

        if (title.isNotEmpty() && content.isNotEmpty()){
            return true
        }
        return false
    }
}