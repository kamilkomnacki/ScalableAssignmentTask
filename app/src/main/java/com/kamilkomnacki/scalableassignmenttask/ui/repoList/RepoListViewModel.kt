package com.kamilkomnacki.scalableassignmenttask.ui.repoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamilkomnacki.scalableassignmenttask.data.GitHubFailure
import com.kamilkomnacki.scalableassignmenttask.data.GitHubRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel(
    private val repository: GitHubRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<RepoListUiState>(RepoListUiState.Loading)
    val uiState: StateFlow<RepoListUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        loadRepositories()
    }

    fun loadRepositories() {
        if (loadJob?.isActive == true) return
        _uiState.value = RepoListUiState.Loading

        loadJob = viewModelScope.launch {
            _uiState.value = try {
                RepoListUiState.Success(repository.getRepos().toImmutableList())
            } catch (failure: GitHubFailure) {
                RepoListUiState.Error(failure.toRepoListError())
            }
        }
    }
}

private fun GitHubFailure.toRepoListError(): RepoListError = when (this) {
    is GitHubFailure.Network -> RepoListError.Network
    is GitHubFailure.Unavailable,
    is GitHubFailure.InvalidResponse -> RepoListError.Generic
}
