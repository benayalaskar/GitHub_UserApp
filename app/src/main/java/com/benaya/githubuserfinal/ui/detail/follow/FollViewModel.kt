package com.benaya.githubuserfinal.ui.detail.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benaya.githubuserfinal.data.remote.response.FollowResponseItem
import com.benaya.githubuserfinal.data.remote.retrofit.ApiConfig
import com.benaya.githubuserfinal.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollViewModel : ViewModel() {
    private val _listFollowing = MutableLiveData<List<FollowResponseItem>>()
    val listFollowing: LiveData<List<FollowResponseItem>> = _listFollowing

    private val _listFollowers = MutableLiveData<List<FollowResponseItem>>()
    val listFollowers: LiveData<List<FollowResponseItem>> = _listFollowers

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    private val _isFound = MutableLiveData<Boolean>()
    val isFound: LiveData<Boolean> = _isFound

    companion object {
        private const val TAG = "FollViewModel"
    }

    fun findFollowingUser(username: String) {
        _isLoading.value = Event(true)
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = Event(false)
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                    if (_listFollowing.value?.isEmpty() == true) {
                        _isFound.value = true
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = Event(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findFollowersUser(username: String) {
        _isLoading.value = Event(true)
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = Event(false)
                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                    if (_listFollowers.value?.isEmpty() == true) {
                        _isFound.value = true
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = Event(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}