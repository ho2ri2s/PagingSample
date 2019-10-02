package com.example.pagingsample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingsample.R
import com.example.pagingsample.data.GithubRepo
import com.example.pagingsample.databinding.ItemGithubBinding

class RepoAdapter : PagedListAdapter<GithubRepo, RepoAdapter.RepoViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.item_github, parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        holder.bind(repo)
    }

    private lateinit var binding: ItemGithubBinding

    inner class RepoViewHolder(private val binding: ItemGithubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: GithubRepo?) {
            binding.repo = repo
        }
    }

    companion object {
        val DiffCallBack = object : DiffUtil.ItemCallback<GithubRepo>() {
            override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
                return oldItem == newItem
            }
        }
    }
}