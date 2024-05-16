package com.benaya.githubuserfinal.ui.home


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.benaya.githubuserfinal.data.remote.response.ItemsItem
import com.benaya.githubuserfinal.databinding.ItemUserBinding
import com.benaya.githubuserfinal.ui.detail.UserActivityDetail
import com.benaya.githubuserfinal.ui.detail.follow.FollFragment

class ListUserAdapter : ListAdapter<ItemsItem, ListUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem) {
            binding.tvName.text = item.login
            binding.tvTypesItem.text = item.type
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .into(binding.ivAvatar)

            itemView.setOnClickListener {
                Intent(itemView.context, UserActivityDetail::class.java).apply {
                    putExtra(UserActivityDetail.EXTRA_LOGIN, item.login)
                    putExtra(UserActivityDetail.EXTRA_AVATAR_URL, item.avatarUrl)
                    putExtra(UserActivityDetail.EXTRA_TYPE, item.type)
                    putExtra(UserActivityDetail.EXTRA_URL, item.url)
                    putExtra(FollFragment.ARG_USERNAME, item.login)
                }.run {
                    itemView.context.startActivity(this)
                }
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}