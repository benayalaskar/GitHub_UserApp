package com.benaya.githubuserfinal.ui.detail.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.benaya.githubuserfinal.data.remote.response.FollowResponseItem
import com.benaya.githubuserfinal.databinding.ItemFollowBinding

class FollAdapter : ListAdapter<FollowResponseItem, FollAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemFollowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(private val binding: ItemFollowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowResponseItem) {
            binding.tvName.text = item.login
            binding.tvTypesItem.text = item.type
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .into(binding.ivAvatar)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowResponseItem>() {
            override fun areItemsTheSame(
                oldItem: FollowResponseItem,
                newItem: FollowResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FollowResponseItem,
                newItem: FollowResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}