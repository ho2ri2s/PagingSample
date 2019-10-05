package com.example.pagingsample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pagingsample.api.GitHubApi
import com.example.pagingsample.data.GithubRepo
import com.example.pagingsample.data.NetworkState
import com.example.pagingsample.paging.RepoDataSourceFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

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

        val client = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

        val api = retrofit.create(GitHubApi::class.java)

        val factory = RepoDataSourceFactory(api, viewModelScope)
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()



        repositories = LivePagedListBuilder(factory, config).build()
        networkState = factory.source.networkState
    }

}