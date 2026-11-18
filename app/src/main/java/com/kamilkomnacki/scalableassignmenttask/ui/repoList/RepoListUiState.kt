package com.kamilkomnacki.scalableassignmenttask.ui.repoList

import androidx.compose.runtime.Immutable
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface RepoListUiState {
    data object Loading : RepoListUiState
    data class Error(val error: RepoListError) : RepoListUiState
    data class Success(val repositories: ImmutableList<Repo>) : RepoListUiState
}

enum class RepoListError {
    Network,
    Generic,
}
