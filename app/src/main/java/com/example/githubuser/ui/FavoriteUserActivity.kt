package com.example.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.model.DetailViewModel
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.ActivityFavoriteUserBinding
import com.google.android.material.appbar.MaterialToolbar

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFavoriteUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewDetailModelFactory = ViewDetailModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels{
            factory
        }


        val toolbar = findViewById<MaterialToolbar>(R.id.menuActivity)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val layoutManager = LinearLayoutManager(this)
        binding.favorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this,layoutManager.orientation)
        binding.favorite.addItemDecoration(itemDecoration)

        showLoading(true)
        detailViewModel.getFavoriteUser().observe(this){users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
           setUsersData(items as List<ItemsItem>)
            if (items != null){
                showLoading(false)
            }
        }
    }


    private fun setUsersData(users: List<ItemsItem>){
        val adapter = UserListAdapter(object : UserListAdapter.OnItemClickListener {
            override fun onItemClick(item: ItemsItem) {
                val intent = Intent(this@FavoriteUserActivity, DetailUserActivity::class.java)
                intent.putExtra("USERNAME", item.login) // or any other data you want to pass
                startActivity(intent)
            }
        })
        adapter.submitList(users)
        binding.favorite.adapter = adapter

    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}