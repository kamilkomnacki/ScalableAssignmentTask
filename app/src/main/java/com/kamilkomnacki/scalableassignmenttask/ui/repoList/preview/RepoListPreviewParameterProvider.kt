package com.kamilkomnacki.scalableassignmenttask.ui.repoList.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo
import com.kamilkomnacki.scalableassignmenttask.ui.repoList.RepoListError
import com.kamilkomnacki.scalableassignmenttask.ui.repoList.RepoListUiState
import kotlinx.collections.immutable.persistentListOf

class RepoListPreviewParameterProvider : PreviewParameterProvider<RepoListUiState> {
    override val values: Sequence<RepoListUiState> = sequenceOf(
        RepoListUiState.Success(
            repositories = persistentListOf(
                Repo(
                    id = 1L,
                    name = "kotlin",
                    description = "The Kotlin programming language.",
                    technology = "Kotlin",
                    stars = 1200,
                ),
                Repo(
                    id = 2L,
                    name = "kotlinx.coroutines",
                    description = "Support for asynchronous programming with Kotlin coroutines.",
                    technology = "Kotlin",
                    stars = 850,
                ),
                Repo(
                    id = 3L,
                    name = "android-notes",
                    description = null,
                    technology = null,
                    stars = 0,
                ),
            ),
        ),
        RepoListUiState.Success(repositories = persistentListOf()),
        RepoListUiState.Error(error = RepoListError.Generic),
        RepoListUiState.Error(error = RepoListError.Network),
        RepoListUiState.Loading,
    )

    override fun getDisplayName(index: Int): String = when (val state = values.elementAt(index)) {
        is RepoListUiState.Success -> if (state.repositories.isEmpty()) "Empty" else "Repositories"
        is RepoListUiState.Error -> "${state.error} error"
        RepoListUiState.Loading -> "Loading"
    }

    companion object {
        const val ACCOUNT = "_user_account_"
    }
}
