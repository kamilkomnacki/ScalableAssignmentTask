package com.kamilkomnacki.scalableassignmenttask.ui.repoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamilkomnacki.scalableassignmenttask.data.GitHubRepository
import com.kamilkomnacki.scalableassignmenttask.pagination.Paginator
import com.kamilkomnacki.scalableassignmenttask.ui.PREFETCH_DISTANCE
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RepoListViewModel(repository: GitHubRepository) : ViewModel() {
    private val paginator = Paginator(
        scope = viewModelScope,
        prefetchDistance = PREFETCH_DISTANCE,
        loadPage = repository::getRepos,
    )

    val uiState: StateFlow<RepoListUiState> = paginator.state
        .map { state -> state.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, RepoListUiState.Loading)

    fun onItemVisible(index: Int) = paginator.onItemVisible(index)

    fun retry() = paginator.retry()
}
