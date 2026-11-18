package com.kamilkomnacki.scalableassignmenttask.ui.commits

import com.kamilkomnacki.scalableassignmenttask.data.GitHubFailure
import com.kamilkomnacki.scalableassignmenttask.data.model.Commit
import com.kamilkomnacki.scalableassignmenttask.pagination.LoadStatus
import com.kamilkomnacki.scalableassignmenttask.pagination.PaginationState
import com.kamilkomnacki.scalableassignmenttask.ui.pagination.toFooter

/**
 * Loaded commits, or a finished empty history, show as [CommitsUiState.Success] with a footer for the next page.
 * Without any commits, a failure covers the whole screen and anything else is still loading.
 */
fun PaginationState<Commit>.toUiState(): CommitsUiState = when {
    items.isNotEmpty() || endReached -> CommitsUiState.Success(
        commits = items,
        footer = status.toFooter(Exception::toCommitsError),
    )
    status is LoadStatus.Failed -> CommitsUiState.Error(status.error.toCommitsError())
    else -> CommitsUiState.Loading
}

// The paginator reports any Exception; only a GitHubFailure carries a reason the screen can explain.
private fun Exception.toCommitsError(): CommitsError = when (this) {
    is GitHubFailure.Network -> CommitsError.Network
    is GitHubFailure.RateLimited -> CommitsError.RateLimited
    else -> CommitsError.Generic
}
