package com.ferreiracaio.rscm_app.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferreiracaio.rscm_app.databinding.PostAdapterBinding
import com.ferreiracaio.rscm_app.models.PostResponse

class PostAdapter(
    private val posts: List<PostResponse>
):RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: PostAdapterBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        with(holder){
            with(posts[position]){
                binding.textAuthorUsername.text = "@${this.author_username}"
                binding.textPostTitle.text = this.title
                binding.textContent.text = this.content
            }
        }
    }

    override fun getItemCount() = posts.size
}