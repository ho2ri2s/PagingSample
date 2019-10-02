package com.example.pagingsample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pagingsample.api.GitHubApi
import com.example.pagingsample.data.GithubRepo
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel: ViewModel() {

    private val _repositories = MutableLiveData<List<GithubRepo>>()
    val repositories: LiveData<List<GithubRepo>>
    get() = _repositories

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val api = retrofit.create(GitHubApi::class.java)
    }
}