package com.benaya.githubuserfinal.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benaya.githubuserfinal.data.UserRepo
import kotlinx.coroutines.launch

class SettingViewModel(private val repo: UserRepo) : ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return repo.getThemeSetting()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repo.saveThemeSetting(isDarkModeActive)
        }
    }
}