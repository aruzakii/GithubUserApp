package com.example.githubuser.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.FavoriteUsersRepository
import com.example.githubuser.data.local.entity.FavoriteUsersEntity
import com.example.githubuser.data.response.DetailUserGithubResponse
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favUsersRepository: FavoriteUsersRepository):ViewModel() {
    private val _dataUser = MutableLiveData<DetailUserGithubResponse>()
    val dataUser : LiveData<DetailUserGithubResponse> = _dataUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private var userName = ""
    private var avatarUrl = ""


    init {
        getUser()
    }

    private fun getUser(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(userName)
        client.enqueue(object : Callback<DetailUserGithubResponse>{
            override fun onResponse(
                call: Call<DetailUserGithubResponse>,
                response: Response<DetailUserGithubResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    _dataUser.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserGithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })

    }

    fun getFavoriteUserbyUsername() = favUsersRepository.getFavoriteUserbyUsername(userName)

    fun deleteFavoriteUservyUsername(){
        favUsersRepository.deleteFavoriteUserbyUsername(userName)
    }

    fun getFavoriteUser() = favUsersRepository.getAllFavoriteUser()

    fun setUsername(name: String){
        userName = name
        getUser()
    }

    fun setAvatarUrl(avatarUrl: String){
        this.avatarUrl = avatarUrl
    }

    fun saveFavUs(){
        val favUser =FavoriteUsersEntity(
            userName,
            avatarUrl,
        )
        favUsersRepository.setFavoriteMarkedUser(favUser)
    }


    companion object{
        private const val TAG = "DetailViewModel"
        private const val QUERRY = "fahrel"
    }


}