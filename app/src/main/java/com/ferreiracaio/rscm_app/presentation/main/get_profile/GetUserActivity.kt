package com.ferreiracaio.rscm_app.presentation.main.get_profile

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferreiracaio.rscm_app.databinding.ActivityGetUserBinding
import com.ferreiracaio.rscm_app.models.UserRequest
import com.ferreiracaio.rscm_app.presentation.main.adapter.PostAdapter
import kotlin.math.log

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
        viewModel.getFollowing(this,user.userId)
        observeIsFollowing()
        viewModel.getUserPosts(this, user.userId)
        observePostList()

        binding.followButton.setOnClickListener {
            viewModel.getFollowing(this,user.userId)
            val isFollowing = viewModel.isFollowing.value
            if(isFollowing == true){
                binding.followButton.setText("Follow User").toString()
                viewModel.unfollowUser(this, user.userId)
            }else{
                viewModel.followUser(this, user.userId)
                binding.followButton.setText("Unfollow User").toString()
            }
        }
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

    private fun observeIsFollowing(){
        viewModel.isFollowing.observe(this){ isFollowing ->
            if (isFollowing){
                Log.d("TAG", "observeIsFollowing: is following")
                binding.followButton.setText("Unfollow User").toString()

            }else{
                Log.d("TAG", "observeIsFollowing: not following")
                binding.followButton.setText("Follow User").toString()
            }
        }
    }

    override fun onDestroy() {
        postAdapter.clearMediaPlayer()
        super.onDestroy()
    }
}