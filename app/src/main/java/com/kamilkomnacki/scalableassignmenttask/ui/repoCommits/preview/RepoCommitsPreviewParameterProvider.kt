package com.kamilkomnacki.scalableassignmenttask.ui.repoCommits.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.kamilkomnacki.scalableassignmenttask.data.model.RepoCommit
import com.kamilkomnacki.scalableassignmenttask.ui.repoCommits.RepoCommitsError
import com.kamilkomnacki.scalableassignmenttask.ui.repoCommits.RepoCommitsUiState
import java.time.LocalDate
import kotlinx.collections.immutable.persistentListOf

class RepoCommitsPreviewParameterProvider : PreviewParameterProvider<RepoCommitsUiState> {
    override val values: Sequence<RepoCommitsUiState> = sequenceOf(
        RepoCommitsUiState.Success(
            commits = persistentListOf(
                RepoCommit(
                    sha = "0123456789abcdef0123456789abcdef01234567",
                    title = "Add repository commit details",
                    author = "Kamil Komnacki",
                    date = LocalDate.of(2026, 11, 18),
                ),
                RepoCommit(
                    sha = "89abcdef0123456789abcdef0123456789abcdef",
                    title = "Display commit details with a very long title that should be " +
                        "limited to two lines and truncated with an ellipsis",
                    author = null,
                    date = LocalDate.of(2026, 11, 17),
                ),
            ),
        ),
        RepoCommitsUiState.Success(commits = persistentListOf()),
        RepoCommitsUiState.Error(error = RepoCommitsError.Generic),
        RepoCommitsUiState.Error(error = RepoCommitsError.Network),
        RepoCommitsUiState.Loading,
    )

    override fun getDisplayName(index: Int): String = when (val state = values.elementAt(index)) {
        is RepoCommitsUiState.Success -> if (state.commits.isEmpty()) "Empty" else "Commits"
        is RepoCommitsUiState.Error -> "${state.error} error"
        RepoCommitsUiState.Loading -> "Loading"
    }

    companion object {
        const val REPOSITORY_NAME = "ScalableAssignmentTask"
    }
}
