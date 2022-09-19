package com.ferreiracaio.rscm_app.presentation.main.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferreiracaio.rscm_app.databinding.UserSearchAdapterBinding
import com.ferreiracaio.rscm_app.models.User
import com.ferreiracaio.rscm_app.models.UserRequest
import com.ferreiracaio.rscm_app.presentation.main.get_profile.GetUserActivity

class UserSearchedAdapter(
    private val users: List<UserRequest>
    ):RecyclerView.Adapter<UserSearchedAdapter.UserSearchViewHolder>(){

    inner class UserSearchViewHolder(val binding: UserSearchAdapterBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchViewHolder {
        val binding = UserSearchAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserSearchViewHolder, position: Int) {
        with(holder){
            with(users[position]){
                binding.textUsername.text = this.username
                binding.textName.text = this.name

                binding.root.setOnClickListener {
                    val activity = holder.binding.root.context as Activity
                    val intent = Intent(activity, GetUserActivity::class.java)
                    intent.putExtra("USER_DETAILS",users[position])

                    activity.startActivity(intent)
                }
            }
        }
    }
    override fun getItemCount() = users.size
}



