package com.benaya.githubuserfinal.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.benaya.githubuserfinal.data.local.entity.FavUserEntity

@Database(
    entities = [FavUserEntity::class],
    version = 1,
    exportSchema = true
)
abstract class GithubUserDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao


    companion object {
        @Volatile
        private var instance: GithubUserDatabase? = null
        fun getInstance(context: Context): GithubUserDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    GithubUserDatabase::class.java, "GithubUserApp.db"
                )
                    .build()
            }
    }
}