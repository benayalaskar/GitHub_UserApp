package com.benaya.githubuserfinal.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.benaya.githubuserfinal.R
import com.benaya.githubuserfinal.databinding.ActivitySettingBinding
import com.benaya.githubuserfinal.util.ViewModelFactory

@Suppress("DEPRECATION")
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbars)
        binding.toolbars.setTitleTextAppearance(
            this@SettingActivity,
            R.style.TextContent_TitleMedium
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        val switchTheme = binding.switchThemes

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val settingViewModel: SettingViewModel by viewModels {
            factory
        }

        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}