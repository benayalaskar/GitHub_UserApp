package com.benaya.githubuserfinal.data.remote.retrofit

import com.benaya.githubuserfinal.data.remote.response.DetailUserResponse
import com.benaya.githubuserfinal.data.remote.response.FollowResponseItem
import com.benaya.githubuserfinal.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getListUser(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowResponseItem>>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowResponseItem>>
}