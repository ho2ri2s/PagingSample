package com.example.pagingsample.paging

import androidx.paging.DataSource
import com.example.pagingsample.api.GitHubApi
import com.example.pagingsample.data.GithubRepo

class RepoDataSourceFactory(private val api: GitHubApi) : DataSource.Factory<Int, GithubRepo>() {

    val source = PageKeyedRepoDataSource(api)
    override fun create(): DataSource<Int, GithubRepo> = source
}