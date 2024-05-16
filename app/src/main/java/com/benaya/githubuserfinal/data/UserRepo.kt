package com.benaya.githubuserfinal.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import com.benaya.githubuserfinal.data.local.entity.FavUserEntity
import com.benaya.githubuserfinal.data.local.room.GithubUserDao
import com.benaya.githubuserfinal.data.remote.retrofit.ApiService
import com.benaya.githubuserfinal.data.setting.SettingPreferences
import com.benaya.githubuserfinal.util.AppExec

class UserRepo private constructor(
    private val apiService: ApiService,
    private val githubUserDao: GithubUserDao,
    private val appExec: AppExec,
    private val pref: SettingPreferences
) {
    private val result = MediatorLiveData<Result<List<FavUserEntity>>>()

    fun setFavUser(favoriteUser: FavUserEntity) {
        appExec.diskIO.execute { githubUserDao.insertFavUser(favoriteUser) }
    }

    fun getFavUserByUsername(username: String): LiveData<FavUserEntity> {
        return githubUserDao.getFavUserByUsername(username)
    }

    fun getListFavUser(): LiveData<List<FavUserEntity>> {
        return githubUserDao.getListFavUser()
    }

    fun deleteFavUser(favoriteUser: FavUserEntity) {
        appExec.diskIO.execute {
            githubUserDao.deleteFavUser(favoriteUser)
        }
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        pref.saveThemeSetting(isDarkModeActive)
    }

    companion object {
        @Volatile
        private var instance: UserRepo? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: GithubUserDao,
            appExec: AppExec,
            pref: SettingPreferences
        ): UserRepo =
            instance ?: synchronized(this) {
                instance ?: UserRepo(apiService, newsDao, appExec, pref)
            }.also { instance = it }
    }
}