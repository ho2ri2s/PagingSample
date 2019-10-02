package com.example.pagingsample.data

import com.squareup.moshi.Json

data class GithubRepo(
    val id: String,
    val name: String,
    val url: String,
    @Json(name = "stargazers_count")
    val star: String
)