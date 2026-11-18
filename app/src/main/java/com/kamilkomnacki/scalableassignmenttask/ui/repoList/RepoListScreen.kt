package com.kamilkomnacki.scalableassignmenttask.ui.repoList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme

@Composable
fun RepoListScreen(
    uiState: RepoListUiState,
    modifier: Modifier = Modifier,
    onRepoClick: (RepoUiModel) -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = uiState.repositories,
                key = { repo -> repo.id },
            ) { repo ->
                RepoRow(
                    repo = repo,
                    onClick = { onRepoClick(repo) },
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
private fun RepoListScreenPreview() {
    ScalableAssignmentTaskTheme {
        RepoListScreen(
            uiState = RepoListUiState(
                repositories = listOf(
                    RepoUiModel(
                        id = 1L,
                        name = "kotlin",
                        owner = "JetBrains",
                        description = "The Kotlin programming language.",
                        technology = "Kotlin",
                        stars = 1200,
                        defaultBranch = "master",
                    ),
                    RepoUiModel(
                        id = 2L,
                        name = "kotlinx.coroutines",
                        owner = "Kotlin",
                        description = "Support for asynchronous programming with Kotlin coroutines.",
                        technology = "Kotlin",
                        stars = 850,
                        defaultBranch = "master",
                    ),
                    RepoUiModel(
                        id = 3L,
                        name = "android-notes",
                        owner = "kamilkomnacki",
                        description = null,
                        technology = null,
                        stars = 0,
                        defaultBranch = "main",
                    ),
                ),
            ),
        )
    }
}
