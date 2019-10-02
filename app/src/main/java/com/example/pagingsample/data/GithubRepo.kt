package com.example.pagingsample.data

import com.squareup.moshi.Json

data class GithubRepo(
    val name: String,
    val url: String,
    @Json(name = "stargazers_count")
    val star: Int
)