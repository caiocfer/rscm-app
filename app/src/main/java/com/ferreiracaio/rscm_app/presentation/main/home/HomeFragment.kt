package com.ferreiracaio.rscm_app.presentation.main.home

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferreiracaio.rscm_app.databinding.FragmentHomeBinding
import com.ferreiracaio.rscm_app.presentation.main.adapter.PostAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel

    private val binding get() = _binding!!

    private lateinit var postAdapter: PostAdapter
    private var mediaPlayer: MediaPlayer? = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        postAdapter = PostAdapter(viewModel.postList,mediaPlayer!!)
        observeLoading()

        val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)

        binding.postRecyclerView.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = postAdapter
        }


        viewModel.getFeed(requireContext())
        observePostList()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observePostList(){
        viewModel.postsLiveData.observe(viewLifecycleOwner) { postList ->
            postList.let {
                postAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun observeLoading(){
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.loadingProgress.visibility = View.VISIBLE
            }else {
                binding.loadingProgress.visibility = View.GONE
            }
        }
    }
}