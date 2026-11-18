package com.kamilkomnacki.scalableassignmenttask.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GitHubApi {
    @GET
    suspend fun getRepos(@Url url: String): Response<List<RepoResponse?>>

    @GET
    suspend fun commits(@Url url: String): Response<List<CommitResponse?>>
}
