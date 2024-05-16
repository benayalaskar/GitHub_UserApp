package com.benaya.githubuserfinal.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benaya.githubuserfinal.data.local.entity.FavUserEntity

@Dao
interface GithubUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavUser(favoriteUser: FavUserEntity)

    @Query("SELECT * FROM favorite_user WHERE username = :username")
    fun getFavUserByUsername(username: String): LiveData<FavUserEntity>

    @Query("SELECT * FROM favorite_user ORDER BY username ASC")
    fun getListFavUser(): LiveData<List<FavUserEntity>>

    @Delete
    fun deleteFavUser(favoriteUser: FavUserEntity)
}