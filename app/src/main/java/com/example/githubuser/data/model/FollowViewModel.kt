package com.example.githubuser.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel:ViewModel() {
    private   var _username = ""
   private var _position = 0

    private val _listUser = MutableLiveData<List<ItemsItem?>>()
    val listUser : LiveData<List<ItemsItem?>> = _listUser

    private val _isLoadiing = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoadiing

    init {
        findUsers()
    }

    private fun findUsers(){
        _isLoadiing.value = true
        if (_position == 1){
            val client = ApiConfig.getApiService().getFollowers(_username)
            client.enqueue(object : Callback<List<ItemsItem>>{
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _isLoadiing.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null){
                        _listUser.value = response.body()
                    }else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _isLoadiing.value = false
                    Log.e(TAG, "onFailure : ${t.message}")
                }
            })
        }else{
            val client = ApiConfig.getApiService().getFollowing(_username)
           client.enqueue(object : Callback<List<ItemsItem>>{
               override fun onResponse(
                   call: Call<List<ItemsItem>>,
                   response: Response<List<ItemsItem>>
               ) {
                   _isLoadiing.value = false
                   val responseBody = response.body()
                   if (response.isSuccessful && responseBody != null){
                       _listUser.value = response.body()
                   }else{
                       Log.e(TAG, "onFailure: ${response.message()}")
                   }
               }

               override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                   _isLoadiing.value = false
                   Log.e(TAG, "onFailure : ${t.message}")
               }
           })
        }


    }

    fun setPosition(post : Int){
        _position = post

    }



    fun setUsername(user: String) {
        _username = user
        findUsers()
    }

    companion object{
        private const val TAG = "FollowViewModel"
        private const val QUERRY = "fahrel"
    }


}