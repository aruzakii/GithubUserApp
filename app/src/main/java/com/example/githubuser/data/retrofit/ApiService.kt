package com.example.githubuser.data.retrofit

import com.example.githubuser.data.response.DetailUserGithubResponse
import com.example.githubuser.data.response.GithubResponse
import com.example.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

     @GET("search/users")
     fun getListUsers(@Query("q") q: String): Call<GithubResponse>


     @GET("users/{username}")
     fun getDetailUser(@Path("username") username: String): Call<DetailUserGithubResponse>


     @GET("users/{username}/followers")
     fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>


     @GET("users/{username}/following")
     fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}