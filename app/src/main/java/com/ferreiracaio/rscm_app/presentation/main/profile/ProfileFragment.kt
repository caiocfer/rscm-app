package com.ferreiracaio.rscm_app.presentation.main.profile

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ferreiracaio.rscm_app.AccessActivity
import com.ferreiracaio.rscm_app.R
import com.ferreiracaio.rscm_app.data.SessionManager
import com.ferreiracaio.rscm_app.databinding.FragmentHomeBinding
import com.ferreiracaio.rscm_app.databinding.FragmentProfileBinding
import kotlin.math.log

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        context?.let { viewModel.getUserData(it) }
        fillUserFields()

        binding.buttonSignOut.setOnClickListener {
            viewModel.signOutUser(requireContext())
            returnToAccessScreen(requireContext())
        }
    }

    private fun fillUserFields(){
        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            if (user != null){
                binding.usernameEditText.setText(user.username)
                binding.nameEditText.setText(user.name)
                binding.emailEditText.setText(user.email)
            }
        }
    }

    private fun returnToAccessScreen(context: Context){
        val intent = Intent(context, AccessActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}