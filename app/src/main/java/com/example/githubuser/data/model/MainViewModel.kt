package com.example.githubuser.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.response.GithubResponse
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.utils.Event
import com.example.githubuser.utils.SettingPreferencecs
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferencecs):ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem?>>()
    val listUser : LiveData<List<ItemsItem?>> = _listUser

    private val _error = MutableLiveData<Event<String>>()
    val error : LiveData<Event<String>> = _error

    private   var _Query = ""

    private val _isLoadiing = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoadiing

    companion object{
        private const val TAG = "MainViewModel"
        private const val QUERRY = "fahrel"
    }
//    init {
//        findUsers()
//    }

    fun run(){
        findUsers()
    }

    private fun findUsers(){
        _isLoadiing.value = true
val client =ApiConfig.getApiService().getListUsers(_Query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
               _isLoadiing.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    _listUser.value = responseBody.items!!
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _error.value  = Event("Terjadi Kesalahan Sistem") //tidak menunjukan error ke user yang sebenarnya takut disalah gunakan
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                 _isLoadiing.value = false
                _error.value  = Event("Terjadi Kesalahan Sistem")
                Log.e(TAG, "onFailure : ${t.message}")

            }
        })

    }



    fun setQuery(query: String) {
        _Query = query
        findUsers()
    }

    fun getThemeSetting(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }




}