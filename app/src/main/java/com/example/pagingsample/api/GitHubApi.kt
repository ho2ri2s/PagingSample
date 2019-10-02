package com.example.pagingsample.api

import com.example.pagingsample.data.GithubRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("/users/{username}/repos")
    fun getGithubRepository(@Path("username") username: String): Call<List<GithubRepo>>
}