package com.kamilkomnacki.scalableassignmenttask.data.remote
/*
import com.example.scalablecapitalandroiddeveloperassignment2.model.RepositoryCommit
import com.google.gson.annotations.SerializedName
import com.kamilkomnacki.scalableassignmenttask.data.invalidResponse
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

// DTO fields are nullable because Gson ignores Kotlin nullability; mapping validates them explicitly.
data class CommitResponse(
    @SerializedName("sha") val sha: String?,
    @SerializedName("commit") val commit: CommitDetails?,
) {
    fun toCommit() = RepositoryCommit(
        sha = sha?.takeIf { it.isNotBlank() } ?: invalidResponse(),
        title = commit?.message?.lineSequence()?.first() ?: invalidResponse(),
        author = commit?.author?.name?.takeIf { it.isNotBlank() },
        date = utcDate(commit?.committer?.date ?: invalidResponse()),
    )
}

// D019: the committer timestamp's UTC calendar date.
private fun utcDate(timestamp: String): LocalDate = try {
    Instant.parse(timestamp).atOffset(ZoneOffset.UTC).toLocalDate()
} catch (invalid: DateTimeException) {
    invalidResponse()
}
*/