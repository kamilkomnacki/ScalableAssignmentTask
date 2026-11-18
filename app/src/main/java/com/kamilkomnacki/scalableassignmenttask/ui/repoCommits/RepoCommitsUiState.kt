package com.kamilkomnacki.scalableassignmenttask.ui.repoCommits

import androidx.compose.runtime.Immutable
import com.kamilkomnacki.scalableassignmenttask.data.model.RepoCommit
import com.kamilkomnacki.scalableassignmenttask.ui.pagination.ListFooter
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface RepoCommitsUiState {
    data object Loading : RepoCommitsUiState
    data class Error(val error: RepoCommitsError) : RepoCommitsUiState
    data class Success(
        val commits: ImmutableList<RepoCommit>,
        val footer: ListFooter<RepoCommitsError> = ListFooter.None,
    ) : RepoCommitsUiState
}

enum class RepoCommitsError {
    Network,
    RateLimited,
    Generic,
}
