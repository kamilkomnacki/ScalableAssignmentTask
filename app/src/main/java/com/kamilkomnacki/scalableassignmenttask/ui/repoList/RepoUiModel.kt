package com.kamilkomnacki.scalableassignmenttask.ui.repoList

data class RepoListUiState(val repositories: List<Repo> = emptyList())

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
