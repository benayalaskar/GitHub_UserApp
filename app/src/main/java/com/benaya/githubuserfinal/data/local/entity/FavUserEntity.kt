package com.benaya.githubuserfinal.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class FavUserEntity(

    @ColumnInfo(name = "username")
    @PrimaryKey(autoGenerate = false)
    var username: String,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "user_url")
    var userUrl: String

) : Parcelable