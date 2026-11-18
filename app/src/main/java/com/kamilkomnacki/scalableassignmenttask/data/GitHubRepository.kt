package com.kamilkomnacki.scalableassignmenttask.data

import com.google.gson.JsonParseException
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo
import com.kamilkomnacki.scalableassignmenttask.data.model.RepoCommit
import com.kamilkomnacki.scalableassignmenttask.data.remote.GitHubApi
import java.io.IOException
import java.net.HttpURLConnection.HTTP_CONFLICT
import retrofit2.Response

class GitHubRepository(
    private val api: GitHubApi,
    private val reposUrl: String,
    private val commitsUrl: (repositoryName: String) -> String,
) {
    suspend fun getRepos(): List<Repo> {
        val response = request { api.getRepos(reposUrl) }

        if (!response.isSuccessful) {
            throw GitHubFailure.Unavailable()
        }

        return response.items { repo -> repo.toRepo() }
    }

    suspend fun getCommits(repoName: String): List<RepoCommit> {
        val response = request { api.commits(commitsUrl(repoName)) }

        // GitHub answers 409 Conflict instead of an empty list for a repository without commits: https://github.com/github/rest-api-description/issues/385
        if (response.code() == HTTP_CONFLICT) {
            return emptyList()
        }
        if (!response.isSuccessful) {
            throw GitHubFailure.Unavailable()
        }

        return response.items { commitResponse -> commitResponse.toRepoCommit() }
    }

    private suspend fun <T> request(call: suspend () -> Response<T>): Response<T> = try {
        call()
    } catch (e: IOException) {
        throw GitHubFailure.Network(e)
    } catch (e: JsonParseException) {
        invalidResponse(e)
    }

    private fun <T, R> Response<List<T?>>.items(map: (T) -> R): List<R> =
        body()
            ?.map { item -> item?.let(map) ?: invalidResponse() }
            ?: invalidResponse()
}
