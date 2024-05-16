package com.benaya.githubuserfinal.di

import android.content.Context
import com.benaya.githubuserfinal.data.UserRepo
import com.benaya.githubuserfinal.data.local.room.GithubUserDatabase
import com.benaya.githubuserfinal.data.remote.retrofit.ApiConfig
import com.benaya.githubuserfinal.data.setting.SettingPreferences
import com.benaya.githubuserfinal.data.setting.dataStore
import com.benaya.githubuserfinal.util.AppExec

object Injection {
    fun provideRepository(context: Context): UserRepo {
        val apiService = ApiConfig.getApiService()
        val database = GithubUserDatabase.getInstance(context)
        val dao = database.githubUserDao()
        val appExec = AppExec()
        val settingPreference = SettingPreferences.getInstance(context.dataStore)
        return UserRepo.getInstance(apiService, dao, appExec, settingPreference)
    }
}