package com.kamilkomnacki.scalableassignmenttask.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubApi {
    // Explicit sorting keeps the order stable between pages; GitHub carries it into the next links.
    @GET("users/{account}/repos?sort=full_name&direction=asc")
    suspend fun getRepos(
        @Path("account") account: String,
        @Query("per_page") perPage: Int = 100,
    ): Response<List<RepoResponse?>>

    @GET("repos/{account}/{repositoryName}/commits")
    suspend fun getCommits(
        @Path("account") account: String,
        @Path("repositoryName") repoName: String,
        @Query("per_page") perPage: Int = 100,
    ): Response<List<CommitResponse?>>

    // Follow the full rel="next" URL from GitHub's Link header, including its query parameters.
    @GET
    suspend fun getReposPage(@Url url: String): Response<List<RepoResponse?>>

    @GET
    suspend fun getCommitsPage(@Url url: String): Response<List<CommitResponse?>>
}
