package com.kamilkomnacki.scalableassignmenttask.ui.repoList

import com.kamilkomnacki.scalableassignmenttask.data.GitHubFailure
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo
import com.kamilkomnacki.scalableassignmenttask.pagination.LoadStatus
import com.kamilkomnacki.scalableassignmenttask.pagination.PaginationState
import com.kamilkomnacki.scalableassignmenttask.pagination.toFooter

/**
 * Loaded repositories, or a finished empty list, show as [RepoListUiState.Success] with a footer for the next page.
 * Without any repositories, a failure covers the whole screen and anything else is still loading.
 */
fun PaginationState<Repo>.toUiState(): RepoListUiState = when {
    items.isNotEmpty() || endReached -> RepoListUiState.Success(
        repositories = items,
        footer = status.toFooter(Exception::toRepoListError),
    )
    status is LoadStatus.Failed -> RepoListUiState.Error(status.error.toRepoListError())
    else -> RepoListUiState.Loading
}

// The paginator reports any Exception; only a GitHubFailure carries a reason the screen can explain.
private fun Exception.toRepoListError(): RepoListError = when (this) {
    is GitHubFailure.Network -> RepoListError.Network
    is GitHubFailure.RateLimited -> RepoListError.RateLimited
    else -> RepoListError.Generic
}
