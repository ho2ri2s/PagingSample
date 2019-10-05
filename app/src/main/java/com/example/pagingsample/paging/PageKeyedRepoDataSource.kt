package com.example.pagingsample.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.pagingsample.api.GitHubApi
import com.example.pagingsample.data.GithubRepo
import com.example.pagingsample.data.NetworkState
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class PageKeyedRepoDataSource(private val api: GitHubApi) : PageKeyedDataSource<Int, GithubRepo>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubRepo>
    ) {
        // 初期化するところ
        callAPI(1, params.requestedLoadSize) { next, repos ->
            callback.onResult(repos, null, next)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubRepo>) {
        // ロード後にする処理
        callAPI(params.key, params.requestedLoadSize) { next, repos ->
            callback.onResult(repos, next)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubRepo>) {
        // ロード前にする処理
    }

    private fun callAPI(
        page: Int, perPage: Int, callback: (next: Int?, repos: List<GithubRepo>) -> Unit
    ) {
        Log.d("MYTAG", "page: $page, perPage: $perPage")

        networkState.postValue(NetworkState.RUNNING)

        var state = NetworkState.FAILED
        try {
            val response = api.getGithubRepository("yanzm", page, perPage).execute()
            Log.d("MYTAG", "$response")
            Log.d("MYTAG", "${response.isSuccessful}")
            if(response.isSuccessful) {
                response.body()?.let {
                    var next: Int? = null
                    //Headerにnextがあれば次ページを加算
                    response.headers().get("Link")?.let {
                        val regex = Regex("rel=\"next\"")
                        if (regex.containsMatchIn(it)) {
                            next = page + 1
                        }
                    }
                    callback(next, it)
                    state = NetworkState.SUCCESS
                }
            }
        } catch (t: Throwable) {
            when(t){
                is IOException -> {t.printStackTrace()}
                is HttpException -> {
                    when(t.code()){
                        HttpURLConnection.HTTP_BAD_REQUEST -> {t.printStackTrace()}
                        HttpURLConnection.HTTP_INTERNAL_ERROR -> {t.printStackTrace()}
                    }
                }
                else -> t.printStackTrace()
            }
        }

        networkState.postValue(state)
    }
}
