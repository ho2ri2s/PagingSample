package com.example.pagingsample.api

import com.example.pagingsample.data.GithubRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("/users/{username}/repos")
    suspend fun getGithubRepository(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<GithubRepo>>
}