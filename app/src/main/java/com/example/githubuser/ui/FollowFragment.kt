package com.example.githubuser.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.model.FollowViewModel
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private val followViewModel: FollowViewModel by viewModels()
    private lateinit var binding:FragmentFollowBinding
    private var position: Int = -1
    private var username: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        followViewModel.setPosition(position)
        followViewModel.setUsername(username.toString())
        followViewModel.listUser.observe(viewLifecycleOwner){list ->
            setUsersData(list as List<ItemsItem>)
        }
        followViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.follow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(),layoutManager.orientation)
        binding.follow.addItemDecoration(itemDecoration)

    }

    private fun setUsersData(users: List<ItemsItem>){
        val adapter = UserListAdapter(object : UserListAdapter.OnItemClickListener {
            override fun onItemClick(item: ItemsItem) {
                val intent = Intent(requireActivity(), DetailUserActivity::class.java)
                intent.putExtra("USERNAME", item.login) // or any other data you want to pass
                intent.putExtra("AVATARURL",item.avatarUrl)
                startActivity(intent)

            }
        })
        adapter.submitList(users)
        binding.follow.adapter = adapter

    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "fahrel"
    }

}