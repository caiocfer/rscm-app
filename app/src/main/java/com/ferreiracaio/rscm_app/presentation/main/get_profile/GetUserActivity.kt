package com.ferreiracaio.rscm_app.presentation.main.get_profile

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferreiracaio.rscm_app.databinding.ActivityGetUserBinding
import com.ferreiracaio.rscm_app.models.UserRequest
import com.ferreiracaio.rscm_app.presentation.main.adapter.PostAdapter

class GetUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetUserBinding
    private lateinit var viewModel: GetUserViewModel
    private lateinit var postAdapter: PostAdapter
    private var mediaPlayer: MediaPlayer? = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(GetUserViewModel::class.java)
        postAdapter = PostAdapter(viewModel.postList,mediaPlayer!!)
        binding = ActivityGetUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        binding.postRecyclerView.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = postAdapter
        }

        val user = intent.extras?.get("USER_DETAILS") as UserRequest
        fillUserFields(user)
        viewModel.getUserPosts(this, user.userId)
        observePostList()
    }

    private fun fillUserFields(user:UserRequest){
        binding.textViewUsername.text = user.username
        binding.textViewName.text = user.name
        binding.textViewEmail.text = user.email
    }

    private fun observePostList(){
        viewModel.postsLiveData.observe(this) { postList ->
            postList.let {
                postAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        postAdapter.clearMediaPlayer()
        super.onDestroy()
    }
}