    package com.example.githubuser.data.local.room

    import android.content.Context
    import androidx.room.Database
    import androidx.room.Room
    import androidx.room.RoomDatabase
    import com.example.githubuser.data.local.entity.FavoriteUsersEntity


    @Database(entities = [FavoriteUsersEntity::class], version = 1, exportSchema = false)
    abstract class FavoriteUsersDatabase : RoomDatabase() {
        abstract fun favusDao():FavoriteUsersDao

        companion object {
            @Volatile
            private var instance: FavoriteUsersDatabase? = null

            fun getInstance(context: Context):FavoriteUsersDatabase =
                instance ?: synchronized(this){
                    instance ?:Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUsersDatabase::class.java,"FavUser.db"
                    ).build()
                }
        }
    }