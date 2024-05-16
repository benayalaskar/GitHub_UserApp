package com.benaya.githubuserfinal.ui.fav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.benaya.githubuserfinal.R
import com.benaya.githubuserfinal.data.remote.response.ItemsItem
import com.benaya.githubuserfinal.databinding.ActivityFavoriteBinding
import com.benaya.githubuserfinal.ui.home.ListUserAdapter
import com.benaya.githubuserfinal.util.ViewModelFactory

@Suppress("DEPRECATION")
class FavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbars)
        binding.toolbars.setTitleTextAppearance(
            this@FavActivity,
            R.style.TextContent_TitleMedium
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val favViewModel: FavViewModel by viewModels {
            factory
        }

        favViewModel.getListFavUser().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(
                    login = it.username,
                    type = it.type,
                    avatarUrl = it.avatarUrl,
                    url = it.userUrl
                )
                items.add(item)
            }
            setListFavoriteUser(items)
        }
    }

    private fun setListFavoriteUser(user: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(user)
        binding.rvUsers.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}