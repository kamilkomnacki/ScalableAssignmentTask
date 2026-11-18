package com.kamilkomnacki.scalableassignmenttask.ui.repoCommits

import com.kamilkomnacki.scalableassignmenttask.data.GitHubFailure
import com.kamilkomnacki.scalableassignmenttask.data.model.RepoCommit
import com.kamilkomnacki.scalableassignmenttask.pagination.LoadStatus
import com.kamilkomnacki.scalableassignmenttask.pagination.PaginationState
import com.kamilkomnacki.scalableassignmenttask.pagination.toFooter

/**
 * Loaded commits, or a finished empty history, show as [RepoCommitsUiState.Success] with a footer for the next page.
 * Without any commits, a failure covers the whole screen and anything else is still loading.
 */
fun PaginationState<RepoCommit>.toUiState(): RepoCommitsUiState = when {
    items.isNotEmpty() || endReached -> RepoCommitsUiState.Success(
        commits = items,
        footer = status.toFooter(Exception::toRepoCommitsError),
    )
    status is LoadStatus.Failed -> RepoCommitsUiState.Error(status.error.toRepoCommitsError())
    else -> RepoCommitsUiState.Loading
}

// The paginator reports any Exception; only a GitHubFailure carries a reason the screen can explain.
private fun Exception.toRepoCommitsError(): RepoCommitsError = when (this) {
    is GitHubFailure.Network -> RepoCommitsError.Network
    is GitHubFailure.RateLimited -> RepoCommitsError.RateLimited
    else -> RepoCommitsError.Generic
}
