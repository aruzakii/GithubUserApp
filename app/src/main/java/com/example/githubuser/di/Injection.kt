package com.example.githubuser.di

import android.content.Context
import com.example.githubuser.data.FavoriteUsersRepository
import com.example.githubuser.data.local.room.FavoriteUsersDatabase
import com.example.githubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteUsersRepository {
        val database = FavoriteUsersDatabase.getInstance(context)
        val dao = database.favusDao()
        val appExecutors = AppExecutors()
        return  FavoriteUsersRepository.getInstance(dao,appExecutors)
    }
}