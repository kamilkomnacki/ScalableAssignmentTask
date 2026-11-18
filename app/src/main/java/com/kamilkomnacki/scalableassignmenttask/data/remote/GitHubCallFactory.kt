package com.kamilkomnacki.scalableassignmenttask.data.remote

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.Request

/**
 * Adds GitHub's REST headers to every call, and the token only for the GitHub API origin.
 * It wraps the transport instead of being an OkHttp interceptor, so tests that replace the
 * transport still see the final requests.
 */
class GitHubCallFactory(
    private val transport: Call.Factory,
    private val authorization: String?,
) : Call.Factory {
    override fun newCall(request: Request): Call = transport.newCall(
        request.newBuilder()
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .build(),
    )
}

// Credentials go only to the GitHub API origin, including on full next links.
private fun HttpUrl.isGitHubApi() =
    scheme == "https" &&
            host == "api.github.com" &&
            port == 443 &&
            username.isEmpty() &&
            password.isEmpty()
