package com.kamilkomnacki.scalableassignmenttask.ui.repoList

import androidx.compose.runtime.Immutable
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo
import com.kamilkomnacki.scalableassignmenttask.ui.pagination.ListFooter
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface RepoListUiState {
    data object Loading : RepoListUiState
    data class Error(val error: RepoListError) : RepoListUiState
    data class Success(
        val repositories: ImmutableList<Repo>,
        val footer: ListFooter<RepoListError> = ListFooter.None,
    ) : RepoListUiState
}

enum class RepoListError {
    Network,
    RateLimited,
    Generic,
}
