package com.kamilkomnacki.scalableassignmenttask.data.remote

import java.io.IOException
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubApiTest {
    @Test
    fun buildsRepositoriesRequestForAccount() {
        val request = requestFor { it.getRepos("test-account") }

        assertEquals("https://api.github.com/users/test-account/repos?sort=full_name&direction=asc&per_page=100", request.url.toString())
    }

    @Test
    fun buildsCommitsRequestForAccountAndRepositoryWithCustomPageSize() {
        val request = requestFor { it.getCommits("test-account", "example", perPage = 50) }

        assertEquals(
            "https://api.github.com/repos/test-account/example/commits?per_page=50",
            request.url.toString(),
        )
    }

    @Test
    fun preservesFullRepositoriesNextPageUrl() {
        val nextUrl = "https://api.github.com/users/test-account/repos?per_page=50&page=2&sort=full_name"

        val request = requestFor { it.getReposPage(nextUrl) }

        assertEquals(nextUrl, request.url.toString())
    }

    @Test
    fun preservesFullCommitsNextPageUrl() {
        val nextUrl = "https://api.github.com/repositories/42/commits?per_page=50&page=3&sha=feature%2Fdetails"

        val request = requestFor { it.getCommitsPage(nextUrl) }

        assertEquals(nextUrl, request.url.toString())
    }

    private fun requestFor(call: suspend (GitHubApi) -> Unit): Request {
        var capturedRequest: Request? = null
        val transportFailure = IOException("Stop after capturing the request; no network call is made.")
        val api = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .callFactory { request ->
                capturedRequest = request
                throw transportFailure
            }
            .build()
            .create(GitHubApi::class.java)

        val thrown = assertThrows(IOException::class.java) {
            runBlocking { call(api) }
        }

        assertEquals(transportFailure.message, thrown.message)
        return requireNotNull(capturedRequest)
    }
}
