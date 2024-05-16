package com.benaya.githubuserfinal.ui.fav

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.benaya.githubuserfinal.data.UserRepo
import com.benaya.githubuserfinal.data.local.entity.FavUserEntity

class FavViewModel(private val userRepo: UserRepo) : ViewModel() {
    fun getListFavUser(): LiveData<List<FavUserEntity>> {
        return userRepo.getListFavUser()
    }
}