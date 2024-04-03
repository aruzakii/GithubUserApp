package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubuser.data.local.entity.FavoriteUsersEntity


@Dao
interface FavoriteUsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavUser(favus: FavoriteUsersEntity)

    @Query("SELECT * FROM favoriteusers WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUsersEntity>

    @Query("DELETE  FROM favoriteusers WHERE username = :username")
    fun deleteFavoriteUserByUsername(username: String)

    @Query("SELECT * FROM favoriteusers")
    fun getFavoriteUser(): LiveData<List<FavoriteUsersEntity>>

    @Update
    fun updateFavUser(favus: FavoriteUsersEntity)
}