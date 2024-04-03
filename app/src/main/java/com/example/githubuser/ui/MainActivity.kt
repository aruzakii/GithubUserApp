package com.example.githubuser.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.model.DetailViewModel
import com.example.githubuser.data.model.MainViewModel
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.utils.SettingPreferencecs
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<MaterialToolbar>(R.id.menuActivity)
        setSupportActionBar(toolbar)
        val pref = SettingPreferencecs.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewMainModelFactory(pref)).get(
            MainViewModel::class.java
        )

        mainViewModel.getThemeSetting().observe(this){isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.listUser.observe(this){list ->
           setUsersData(list as List<ItemsItem>)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        mainViewModel.error.observe(this,{
            it.getContentIfNotHandled()?.let{err ->
                Snackbar.make(window.decorView.rootView, err, Snackbar.LENGTH_SHORT).show()
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this,layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)


        binding.searchBar.setOnClickListener  {
            binding.searchView.setupWithSearchBar(binding.searchBar)
            binding.searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event->
                    val query = textView.text.toString()
                    binding.searchBar.setText(query)
                    binding.searchView.hide()
                    mainViewModel.setQuery(query)
                    mainViewModel.run()
                    false
                }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu_appbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           R.id.menu_favorite ->{
                val intent = Intent(this@MainActivity,FavoriteUserActivity::class.java)
                startActivity(intent)
            }
            R.id.setting ->{
                val intent = Intent(this@MainActivity,DarkLightActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUsersData(users: List<ItemsItem>){
        val adapter = UserListAdapter(object : UserListAdapter.OnItemClickListener {
            override fun onItemClick(item: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra("USERNAME", item.login) // or any other data you want to pass
                intent.putExtra("AVATARURL",item.avatarUrl)
                startActivity(intent)
            }
        })
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter

    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }



}