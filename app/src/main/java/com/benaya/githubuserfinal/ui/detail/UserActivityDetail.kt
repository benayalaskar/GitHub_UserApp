package com.benaya.githubuserfinal.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.benaya.githubuserfinal.R
import com.benaya.githubuserfinal.data.local.entity.FavUserEntity
import com.benaya.githubuserfinal.data.remote.response.DetailUserResponse
import com.benaya.githubuserfinal.databinding.ActivityDetailUserBinding
import com.benaya.githubuserfinal.ui.detail.follow.SectionsPagerAdapter
import com.benaya.githubuserfinal.util.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class UserActivityDetail : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val EXTRA_LOGIN = "extra_login"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_URL = "extra_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2
        )
    }

    private var isFav = false
    private var favoriteUser: FavUserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModelDetail: ViewModelDetail by viewModels {
            factory
        }

        setSupportActionBar(binding.detailToolbar)
        binding.detailToolbar.setTitleTextAppearance(
            this@UserActivityDetail,
            R.style.TextContent_TitleMedium
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.tabLayouts.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabLayouts.toptab
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra(EXTRA_LOGIN)
        val avatarURl = intent.getStringExtra(EXTRA_AVATAR_URL)
        val type = intent.getStringExtra(EXTRA_TYPE)
        val url = intent.getStringExtra(EXTRA_URL)

        val btnFavorite = binding.btnFav

        viewModelDetail.isLoading.observe(this) {
            showLoading(it)
        }

        viewModelDetail.detailUser.observe(this) { detailUser ->
            setDetailUserData(detailUser)
        }



        if (username != null) {
            viewModelDetail.findDetailUser(username)
        }

        viewModelDetail.getFavoriteUserByUsername(username.toString()).observe(this) {
            favoriteUser = it
            isFav = if (favoriteUser != null) {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        btnFavorite.context,
                        R.drawable.baseline_favorite_24
                    )
                )
                true
            } else {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        btnFavorite.context,
                        R.drawable.baseline_favorite_border_24
                    )
                )
                false

            }
        }

        binding.btnFav.setOnClickListener {
            if (isFav) {
                viewModelDetail.deleteFavoriteUser(favoriteUser as FavUserEntity)
            } else {
                viewModelDetail.setFavoriteUser(
                    FavUserEntity(
                        username.toString(),
                        avatarURl.toString(),
                        type.toString(),
                        url.toString()
                    )
                )

            }
        }

        binding.detailToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_share -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        val content = "${username}, \n${url}"
                        putExtra(Intent.EXTRA_TEXT, content)
                        this.type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                    true
                }

                else -> false
            }
        }

    }

    private fun setDetailUserData(detailUser: DetailUserResponse) {
        binding.tvDetailsName.text = detailUser.name
        binding.tvDetailsUsername.text = detailUser.login
        binding.tvDetailFollNum.text = detailUser.following.toString()
        binding.tvDetailFollowersNum.text = detailUser.followers.toString()
        binding.tvDetailReposNum.text = detailUser.publicRepos.toString()
        binding.detailToolbar.title = detailUser.name

        Glide.with(this)
            .load(detailUser.avatarUrl)
            .into(binding.ivDetailsAvatar)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }
}