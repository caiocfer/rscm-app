package com.ferreiracaio.rscm_app.presentation.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferreiracaio.rscm_app.databinding.FragmentSearchBinding
import com.ferreiracaio.rscm_app.presentation.main.adapter.UserSearchedAdapter


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var viewModel: SearchViewModel
    private lateinit var userAdapter: UserSearchedAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        userAdapter = UserSearchedAdapter(viewModel.userList)

        val linearLayoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)

        binding.userRecyclerView.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = userAdapter
        }

        observeUserList()

        binding.searchUserBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    viewModel.searchUsers(requireContext(),query)
                    userAdapter.notifyDataSetChanged()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeUserList(){
        viewModel.usersLiveData.observe(viewLifecycleOwner) { userList ->
            userList.let {
                userAdapter.notifyItemRangeInserted(viewModel.userList.size, 25)
            }
        }
    }
}