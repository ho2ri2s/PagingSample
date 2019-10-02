package com.example.pagingsample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pagingsample.api.GitHubApi
import com.example.pagingsample.data.GithubRepo
import com.example.pagingsample.data.NetworkState
import com.example.pagingsample.paging.RepoDataSourceFactory
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 50
    }

    val repositories: LiveData<PagedList<GithubRepo>>

    val networkState: LiveData<NetworkState>

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val api = retrofit.create(GitHubApi::class.java)

        val factory = RepoDataSourceFactory(api)
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()

        repositories = LivePagedListBuilder(factory, config).build()
        networkState = factory.source.networkState
    }
}