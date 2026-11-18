package com.kamilkomnacki.scalableassignmenttask

import android.app.Application
import com.kamilkomnacki.scalableassignmenttask.data.remote.GitHubApi
import com.kamilkomnacki.scalableassignmenttask.data.remote.GitHubCallFactory
import com.kamilkomnacki.scalableassignmenttask.data.GitHubRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScalableApplication : Application() {
    val gitHubRepository by lazy {
        val authorization = BuildConfig.GITHUB_TOKEN.takeIf { it.isNotBlank() }?.let { "Bearer $it" }
        val api = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .callFactory(GitHubCallFactory(OkHttpClient(), authorization))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)

        GitHubRepository(
            api = api,
            account = ACCOUNT,
        )
    }

    companion object {
        const val ACCOUNT = "mattpocock"
    }
}
