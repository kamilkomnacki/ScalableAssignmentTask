package com.kamilkomnacki.scalableassignmenttask.ui.repoCommits

import androidx.lifecycle.ViewModel
import com.kamilkomnacki.scalableassignmenttask.data.model.RepoCommit
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class RepoCommitsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        RepoCommitsUiState(
            commits = persistentListOf(
                RepoCommit(
                    sha = "a1b2c3d4e5f67890123456789012345678901234",
                    title = "Add repository commits screen",
                    author = "Kamil Komnacki",
                    date = LocalDate.of(2026, 11, 18),
                ),
                RepoCommit(
                    sha = "b2c3d4e5f678901234567890123456789012345a1",
                    title = "Display repository language and star count",
                    author = "Alex Smith",
                    date = LocalDate.of(2026, 11, 17),
                ),
                RepoCommit(
                    sha = "c3d4e5f678901234567890123456789012345a1b2",
                    title = "Fix long repository names overflowing the list row",
                    author = null,
                    date = LocalDate.of(2026, 11, 16),
                ),
                RepoCommit(
                    sha = "d4e5f678901234567890123456789012345a1b2c3",
                    title = "Add loading and error states to the repository list",
                    author = "Kamil Komnacki",
                    date = LocalDate.of(2026, 11, 15),
                ),
                RepoCommit(
                    sha = "e5f678901234567890123456789012345a1b2c3d4",
                    title = "Create initial application theme",
                    author = "Alex Smith",
                    date = LocalDate.of(2026, 11, 14),
                ),
            ).addingAll(
                List(25) { index ->
                    RepoCommit(
                        sha = (index + 6).toString(16).padStart(7, '0').padEnd(40, '0'),
                        title = "Example commit ${index + 6}: update repository details",
                        author = if (index % 3 == 0) null else "Alex Smith",
                        date = LocalDate.of(2026, 11, 13).minusDays(index.toLong()),
                    )
                },
            ),
        ),
    )
    val uiState: StateFlow<RepoCommitsUiState> = _uiState.asStateFlow()
}
