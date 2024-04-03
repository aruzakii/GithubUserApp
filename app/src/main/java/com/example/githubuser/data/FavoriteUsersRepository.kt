package com.example.githubuser.data

import androidx.lifecycle.LiveData
import com.example.githubuser.data.local.entity.FavoriteUsersEntity
import com.example.githubuser.data.local.room.FavoriteUsersDao
import com.example.githubuser.utils.AppExecutors

class FavoriteUsersRepository private constructor(
    private val favUsersDao: FavoriteUsersDao,
    private val appExecutors: AppExecutors
){
    fun getAllFavoriteUser():LiveData<List<FavoriteUsersEntity>>{
        return favUsersDao.getFavoriteUser()
    }

    fun setFavoriteMarkedUser(favus: FavoriteUsersEntity){
        appExecutors.diskIO.execute{
            favUsersDao.insertFavUser(favus)
        }
    }

    fun getFavoriteUserbyUsername(username: String):LiveData<FavoriteUsersEntity>{
        return favUsersDao.getFavoriteUserByUsername(username)
    }

    fun deleteFavoriteUserbyUsername(username: String){
        appExecutors.diskIO.execute {
            favUsersDao.deleteFavoriteUserByUsername(username)
        }
    }



    companion object {
        @Volatile
        private var instance: FavoriteUsersRepository? = null
        fun getInstance(
            newsDao: FavoriteUsersDao,
            appExecutors: AppExecutors,
        ): FavoriteUsersRepository =
            instance ?: synchronized(this){
                instance ?: FavoriteUsersRepository(newsDao, appExecutors)
            }.also { instance = it }
    }
}