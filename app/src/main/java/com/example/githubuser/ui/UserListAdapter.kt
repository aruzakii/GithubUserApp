package com.example.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.ItemUserBinding

class UserListAdapter(private val listener: OnItemClickListener): ListAdapter<ItemsItem, UserListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemUserBinding, private val listener: OnItemClickListener): RecyclerView.ViewHolder(binding.root) {
        fun bind(users: ItemsItem){
            binding.username.text = "${users.login}"
            Glide.with(binding.root.context)
                .load(users.avatarUrl)
                .transform(CircleCrop())
                .into(binding.profil)

            binding.root.setOnClickListener {
                listener.onItemClick(users)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: UserListAdapter.MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }




    interface OnItemClickListener {
        fun onItemClick(item: ItemsItem)
    }


    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return  oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return  oldItem == newItem
            }
        }
    }
}