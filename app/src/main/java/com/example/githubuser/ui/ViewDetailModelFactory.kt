package com.example.githubuser.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.FavoriteUsersRepository
import com.example.githubuser.data.model.DetailViewModel
import com.example.githubuser.di.Injection

class ViewDetailModelFactory private constructor(private val favUsersRepository: FavoriteUsersRepository):
ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(favUsersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance : ViewDetailModelFactory? = null
        fun getInstance(context: Context): ViewDetailModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewDetailModelFactory(Injection.provideRepository(context))
            }.also { instance =  it }
    }

}