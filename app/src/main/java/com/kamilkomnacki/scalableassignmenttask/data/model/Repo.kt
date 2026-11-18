package com.kamilkomnacki.scalableassignmenttask.data.model

data class Repo(
    val id: Long,
    val name: String,
    val owner: String,
    val description: String?,
    val technology: String?,
    val stars: Int,
    val defaultBranch: String?,
)

data class RepoPage(val repositories: List<Repo>, val nextUrl: String?)
