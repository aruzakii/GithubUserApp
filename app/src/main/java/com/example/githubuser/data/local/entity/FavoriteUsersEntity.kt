package com.example.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favoriteusers")
@Parcelize
class FavoriteUsersEntity(
    @ColumnInfo(name = "username")
    @PrimaryKey(autoGenerate = false)
    var username: String = "",

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,

):Parcelable