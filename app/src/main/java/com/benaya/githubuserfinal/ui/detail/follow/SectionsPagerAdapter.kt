package com.benaya.githubuserfinal.ui.detail.follow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var username = ""

        val fragment = FollFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollFragment.ARG_POSITION, position + 1)
            putString(FollFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}