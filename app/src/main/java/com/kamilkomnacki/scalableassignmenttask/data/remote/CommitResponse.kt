package com.kamilkomnacki.scalableassignmenttask.data.remote

import com.google.gson.annotations.SerializedName
import com.kamilkomnacki.scalableassignmenttask.data.invalidResponse
import com.kamilkomnacki.scalableassignmenttask.data.model.RepoCommit
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

data class CommitResponse(
    @SerializedName("sha") val sha: String?,
    @SerializedName("commit") val commit: CommitDetailsResponse?,
) {
    fun toCommit() = RepoCommit(
        sha = sha?.takeIf { it.isNotBlank() } ?: invalidResponse(),
        title = commit?.message?.lineSequence()?.first() ?: invalidResponse(),
        author = commit?.author?.name?.takeIf { it.isNotBlank() },
        date = utcDate(commit?.committer?.date ?: invalidResponse()),
    )
}

data class CommitDetailsResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("author") val author: CommitAuthorResponse?,
    @SerializedName("committer") val committer: CommitterResponse?,
)

data class CommitAuthorResponse(
    @SerializedName("name") val name: String?,
)

data class CommitterResponse(
    @SerializedName("date") val date: String?,
)

private fun utcDate(timestamp: String): LocalDate = try {
    Instant.parse(timestamp).atOffset(ZoneOffset.UTC).toLocalDate()
} catch (invalid: DateTimeException) {
    invalidResponse(invalid)
}
