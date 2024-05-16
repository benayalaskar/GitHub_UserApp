package com.benaya.githubuserfinal.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benaya.githubuserfinal.data.UserRepo
import com.benaya.githubuserfinal.di.Injection
import com.benaya.githubuserfinal.ui.detail.ViewModelDetail
import com.benaya.githubuserfinal.ui.fav.FavViewModel
import com.benaya.githubuserfinal.ui.home.MainViewModel
import com.benaya.githubuserfinal.ui.setting.SettingViewModel

class ViewModelFactory private constructor(private val userRepo: UserRepo) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelDetail::class.java)) {
            return ViewModelDetail(userRepo) as T
        } else if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(userRepo) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(userRepo) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepo) as T
        }
        throw IllegalArgumentException("Error! Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}