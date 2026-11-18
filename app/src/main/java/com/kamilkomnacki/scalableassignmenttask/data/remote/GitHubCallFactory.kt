package com.kamilkomnacki.scalableassignmenttask.data.remote

import okhttp3.Call
import okhttp3.Request

/**
 * Adds GitHub's REST headers to every call.
 * It wraps the transport instead of being an OkHttp interceptor, so tests that replace the
 * transport still see the final requests.
 */
class GitHubCallFactory(
    private val transport: Call.Factory,
) : Call.Factory {
    override fun newCall(request: Request): Call = transport.newCall(
        request.newBuilder()
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .build(),
    )
}
