package com.kamilkomnacki.scalableassignmenttask.ui.commits

import androidx.compose.runtime.Immutable
import com.kamilkomnacki.scalableassignmenttask.data.model.Commit
import com.kamilkomnacki.scalableassignmenttask.ui.pagination.ListFooter
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface CommitsUiState {
    data object Loading : CommitsUiState
    data class Error(val error: CommitsError) : CommitsUiState
    data class Success(
        val commits: ImmutableList<Commit>,
        val footer: ListFooter<CommitsError> = ListFooter.None,
    ) : CommitsUiState
}

enum class CommitsError {
    Network,
    RateLimited,
    Generic,
}
