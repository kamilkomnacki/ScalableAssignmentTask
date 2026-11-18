package com.kamilkomnacki.scalableassignmenttask.data.remote

import com.google.gson.annotations.SerializedName
import com.kamilkomnacki.scalableassignmenttask.data.invalidResponse
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo

// Gson bypasses the Kotlin constructor: missing fields become null (objects) or 0 (primitives),
// and default values are ignored. All fields are nullable so toRepo() can reject missing required ones.
data class RepoResponse(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("stargazers_count") val stars: Int?,
) {
    fun toRepo() = Repo(
        id = id ?: invalidResponse(),
        name = name?.takeIf { it.isNotBlank() } ?: invalidResponse(),
        description = description?.takeIf { it.isNotBlank() },
        technology = language?.takeIf { it.isNotBlank() },
        stars = stars ?: invalidResponse(),
    )
}
