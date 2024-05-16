package com.benaya.githubuserfinal.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.benaya.githubuserfinal.R
import com.benaya.githubuserfinal.data.remote.response.ItemsItem
import com.benaya.githubuserfinal.databinding.ActivityMainBinding
import com.benaya.githubuserfinal.ui.fav.FavActivity
import com.benaya.githubuserfinal.ui.setting.SettingActivity
import com.benaya.githubuserfinal.util.ViewModelFactory
import com.google.android.material.search.SearchView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    companion object {
        private var USER_LOGIN = "user_login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedCallback =
            this@MainActivity.onBackPressedDispatcher.addCallback(this@MainActivity, false) {
                binding.searchView.hide()
            }

        binding.toolbars.setTitleTextAppearance(this@MainActivity, R.style.TextContent_TitleLarge)

        val layoutManager = LinearLayoutManager(this)
        binding.itemListUser.rvUsers.layoutManager = layoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val mainViewModel: MainViewModel by viewModels {
            factory
        }

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            setTheme(isDarkModeActive)
        }

        mainViewModel.listUser.observe(this) { listUser ->
            setListUser(listUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.isFound.observe(this) {
            showDataIsFound(it)
        }

        with(binding) {
            searchView.apply {
                searchView.setupWithSearchBar(searchBar)
                addTransitionListener { _, _, newState ->
                    onBackPressedCallback.isEnabled =
                        newState == SearchView.TransitionState.SHOWN || newState == SearchView.TransitionState.SHOWING
                }

                editText.setOnEditorActionListener { _, _, _ ->
                    USER_LOGIN = searchView.text.toString()
                    searchView.hide()
                    mainViewModel.findUser(USER_LOGIN)
                    false
                }
            }
        }

        binding.toolbars.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val intent = Intent(this@MainActivity, FavActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu2 -> {
                    val intent = Intent(this@MainActivity, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun setListUser(user: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(user)
        binding.itemListUser.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.itemListUser.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showDataIsFound(isFound: Boolean) {
        if (isFound) {
            binding.itemListUser.notFound.visibility = View.VISIBLE
            binding.itemListUser.tvDataNotFound.visibility = View.VISIBLE
            binding.itemListUser.ivNotFound.visibility = View.VISIBLE
            binding.itemListUser.ivSearch.visibility = View.INVISIBLE
        } else {
            binding.itemListUser.notFound.visibility = View.INVISIBLE
            binding.itemListUser.tvDataNotFound.visibility = View.INVISIBLE
            binding.itemListUser.ivNotFound.visibility = View.INVISIBLE
            binding.itemListUser.ivSearch.visibility = View.INVISIBLE
        }
    }

    private fun setTheme(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}