package com.kamilkomnacki.scalableassignmenttask.ui.repoCommits

import androidx.compose.runtime.Immutable
import com.kamilkomnacki.scalableassignmenttask.data.model.RepoCommit
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class RepoCommitsUiState(
    val commits: ImmutableList<RepoCommit> = persistentListOf(),
)
