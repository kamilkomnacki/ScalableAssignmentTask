package com.kamilkomnacki.scalableassignmenttask.ui.repoCommits

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

class RepoCommitsViewModel(
    private val repository: GitHubRepository,
    private val repositoryName: String,
) : ViewModel() {
    private val _uiState = MutableStateFlow<RepoCommitsUiState>(RepoCommitsUiState.Loading)
    val uiState: StateFlow<RepoCommitsUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        loadCommits()
    }

    fun loadCommits() {
        if (loadJob?.isActive == true) return
        _uiState.value = RepoCommitsUiState.Loading

        loadJob = viewModelScope.launch {
            _uiState.value = try {
                RepoCommitsUiState.Success(repository.getCommits(repositoryName).toImmutableList())
            } catch (failure: GitHubFailure) {
                RepoCommitsUiState.Error(failure.toRepoCommitsError())
            }
        }
    }
}

private fun GitHubFailure.toRepoCommitsError(): RepoCommitsError = when (this) {
    is GitHubFailure.Network -> RepoCommitsError.Network
    is GitHubFailure.RateLimited -> RepoCommitsError.RateLimited
    is GitHubFailure.TokenRejected -> RepoCommitsError.TokenRejected
    is GitHubFailure.Unavailable,
    is GitHubFailure.InvalidResponse -> RepoCommitsError.Generic
}
