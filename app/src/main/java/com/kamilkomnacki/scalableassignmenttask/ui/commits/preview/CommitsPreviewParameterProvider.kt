package com.kamilkomnacki.scalableassignmenttask.ui.commits.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.kamilkomnacki.scalableassignmenttask.data.model.Commit
import com.kamilkomnacki.scalableassignmenttask.ui.commits.CommitsError
import com.kamilkomnacki.scalableassignmenttask.ui.commits.CommitsUiState
import com.kamilkomnacki.scalableassignmenttask.ui.pagination.ListFooter
import java.time.LocalDate
import kotlinx.collections.immutable.persistentListOf

class CommitsPreviewParameterProvider : PreviewParameterProvider<CommitsUiState> {
    private val commits = persistentListOf(
        Commit(
            sha = "0123456789abcdef0123456789abcdef01234567",
            title = "Add repository commit details",
            author = "Kamil Komnacki",
            date = LocalDate.of(2026, 11, 18),
        ),
        Commit(
            sha = "89abcdef0123456789abcdef0123456789abcdef",
            title = "Display commit details with a very long title that should be " +
                "limited to two lines and truncated with an ellipsis",
            author = null,
            date = LocalDate.of(2026, 11, 17),
        ),
    )

    override val values: Sequence<CommitsUiState> = sequenceOf(
        CommitsUiState.Success(commits = commits),
        CommitsUiState.Success(commits = commits, footer = ListFooter.Loading),
        CommitsUiState.Success(commits = commits, footer = ListFooter.Error(CommitsError.Generic)),
        CommitsUiState.Success(commits = persistentListOf()),
        CommitsUiState.Error(error = CommitsError.Generic),
        CommitsUiState.Error(error = CommitsError.Network),
        CommitsUiState.Error(error = CommitsError.RateLimited),
        CommitsUiState.Loading,
    )

    override fun getDisplayName(index: Int): String = when (val state = values.elementAt(index)) {
        is CommitsUiState.Success -> when {
            state.commits.isEmpty() -> "Empty"
            state.footer == ListFooter.None -> "Commits"
            else -> "Commits with ${state.footer} footer"
        }
        is CommitsUiState.Error -> "${state.error} error"
        CommitsUiState.Loading -> "Loading"
    }

    companion object {
        const val REPOSITORY_NAME = "ScalableAssignmentTask"
    }
}
