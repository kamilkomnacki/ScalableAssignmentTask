package com.kamilkomnacki.scalableassignmenttask.data

sealed class GitHubFailure(cause: Throwable? = null) : Exception(cause) {
    class Network(cause: Throwable) : GitHubFailure(cause)
    class RateLimited : GitHubFailure()
    class Unavailable : GitHubFailure()
    class InvalidResponse(cause: Throwable? = null) : GitHubFailure(cause)
}

fun invalidResponse(cause: Throwable? = null): Nothing =
    throw GitHubFailure.InvalidResponse(cause)
