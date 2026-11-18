package com.kamilkomnacki.scalableassignmenttask.data

import com.google.gson.JsonParseException
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo
import com.kamilkomnacki.scalableassignmenttask.data.model.RepoCommit
import com.kamilkomnacki.scalableassignmenttask.data.remote.GitHubApi
import com.kamilkomnacki.scalableassignmenttask.data.remote.isGitHubApi
import com.kamilkomnacki.scalableassignmenttask.data.remote.nextLink
import com.kamilkomnacki.scalableassignmenttask.pagination.Page
import java.io.IOException
import java.net.HttpURLConnection.HTTP_CONFLICT
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import retrofit2.Response

/**
 * Loads GitHub lists one page at a time. A null `nextUrl` loads the first page; otherwise it is
 * the full rel="next" URL from the previous page, and the returned [Page.nextCursor] is the next one.
 */
class GitHubRepository(
    private val api: GitHubApi,
    private val account: String,
) {
    suspend fun getRepos(nextUrl: String? = null): Page<Repo> {
        if (nextUrl != null) requireGitHubApiUrl(nextUrl)
        val response = request {
            if (nextUrl == null) {
                api.getRepos(account)
            } else {
                api.getReposPage(nextUrl)
            }
        }

        return response.page { repo -> repo.toRepo() }
    }

    suspend fun getCommits(repoName: String, nextUrl: String? = null): Page<RepoCommit> {
        if (nextUrl != null) requireGitHubApiUrl(nextUrl)
        val response = request {
            if (nextUrl == null) {
                api.commits(account, repoName)
            } else {
                api.commitsPage(nextUrl)
            }
        }

        // GitHub answers 409 Conflict instead of an empty list for a repository without commits: https://github.com/github/rest-api-description/issues/385
        // A later page can't belong to an empty repository, so there 409 stays a failure instead of silently cutting the history.
        if (response.code() == HTTP_CONFLICT && nextUrl == null) {
            return Page(items = emptyList(), nextCursor = null)
        }
        return response.page { commitResponse -> commitResponse.toRepoCommit() }
    }

    private fun requireGitHubApiUrl(nextUrl: String) {
        if (nextUrl.toHttpUrlOrNull()?.isGitHubApi() != true) {
            invalidResponse()
        }
    }

    private suspend fun <T> request(call: suspend () -> Response<T>): Response<T> = try {
        call().also { response ->
            if (response.isRateLimited()) throw GitHubFailure.RateLimited()
        }
    } catch (e: IOException) {
        throw GitHubFailure.Network(e)
    } catch (e: JsonParseException) {
        invalidResponse(e)
    }

    private fun <T, R> Response<List<T?>>.page(map: (T) -> R): Page<R> =
        Page(
            items = items(map),
            nextCursor = nextLink(headers())
        )

    private fun <T, R> Response<List<T?>>.items(map: (T) -> R): List<R> {
        if (!isSuccessful) {
            throw GitHubFailure.Unavailable()
        }
        return body()
            ?.map { item -> item?.let(map) ?: invalidResponse() }
            ?: invalidResponse()
    }
}

private const val HTTP_TOO_MANY_REQUESTS = 429

private fun Response<*>.isRateLimited() =
    code() == HTTP_TOO_MANY_REQUESTS ||
        (code() == HTTP_FORBIDDEN && (hourlyLimitExhausted() || serverRequestsRetryDelay()))

private fun Response<*>.hourlyLimitExhausted() = headers()["X-RateLimit-Remaining"] == "0"

private fun Response<*>.serverRequestsRetryDelay() = headers()["Retry-After"] != null
