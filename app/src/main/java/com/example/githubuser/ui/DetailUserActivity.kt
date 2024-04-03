package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.githubuser.R
import com.example.githubuser.data.model.DetailViewModel
import com.example.githubuser.data.response.DetailUserGithubResponse
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailUserBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var username = intent.getStringExtra("USERNAME")
        var avatarUrl = intent.getStringExtra("AVATARURL")


        val toolbar = findViewById<MaterialToolbar>(R.id.menuActivity)

        toolbar.setNavigationOnClickListener {
          finish()
        }

        val factory: ViewDetailModelFactory = ViewDetailModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels{
            factory
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this,username.toString())
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)


        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs,viewPager) {tab,position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()


        detailViewModel.setUsername(username.toString())
        detailViewModel.setAvatarUrl(avatarUrl.toString())

        detailViewModel.getFavoriteUserbyUsername().observe(this){result->
            if (result == null){
                binding.fabAdd.setImageResource(R.drawable.ic_belom_favorite)
                binding.fabAdd.setOnClickListener{
                    detailViewModel.saveFavUs()
                }
            }else{
                binding.fabAdd.setImageResource(R.drawable.ic_sudah_favorite)
                binding.fabAdd.setOnClickListener{
                    detailViewModel.deleteFavoriteUservyUsername()

                }
            }

        }

        detailViewModel.dataUser.observe(this){data ->
            setUserDetailData(data)
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }




    }

    fun setUserDetailData(data : DetailUserGithubResponse){
        binding.name.text = data.name
        binding.username.text = data.login
        binding.followers.text = "Followers ${data.followers.toString()}"
        binding.following.text = "Following ${data.following.toString()}"

        Glide.with(this)
            .load(data.avatarUrl)
            .transform(CircleCrop())
            .into(binding.imgDetailProfile)
    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text1,
            R.string.tab_text2
        )
    }


}