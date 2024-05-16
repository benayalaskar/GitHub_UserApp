package com.benaya.githubuserfinal.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benaya.githubuserfinal.data.UserRepo
import com.benaya.githubuserfinal.data.local.entity.FavUserEntity
import com.benaya.githubuserfinal.data.remote.response.DetailUserResponse
import com.benaya.githubuserfinal.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelDetail(private val userRepo: UserRepo) : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setFavoriteUser(favoriteUser: FavUserEntity) {
        userRepo.setFavUser(favoriteUser)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavUserEntity> {
        return userRepo.getFavUserByUsername(username)
    }

    fun deleteFavoriteUser(favoriteUser: FavUserEntity) =
        userRepo.deleteFavUser(favoriteUser)

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "Error! onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Error! onFailure: ${t.message}")
            }
        })
    }

}