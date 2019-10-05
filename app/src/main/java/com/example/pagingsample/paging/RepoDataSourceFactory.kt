package com.example.pagingsample.paging

import androidx.paging.DataSource
import com.example.pagingsample.api.GitHubApi
import com.example.pagingsample.data.GithubRepo
import kotlinx.coroutines.CoroutineScope

class RepoDataSourceFactory(api: GitHubApi, scope: CoroutineScope) : DataSource.Factory<Int, GithubRepo>() {

    val source = PageKeyedRepoDataSource(api, scope)
    override fun create(): DataSource<Int, GithubRepo> = source
}