package com.kamilkomnacki.scalableassignmenttask.ui.repoCommits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamilkomnacki.scalableassignmenttask.R
import com.kamilkomnacki.scalableassignmenttask.data.model.RepoCommit
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoCommitsScreen(
    uiState: RepoCommitsUiState,
    repositoryName: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.repo_commits_title_with_name, repositoryName),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            if (uiState.commits.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_commits),
                    modifier = Modifier.padding(16.dp),
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = uiState.commits,
                        key = { commit -> commit.sha },
                    ) { commit ->
                        RepoCommitRow(commit = commit)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
private fun RepoCommitsScreenPreview() {
    ScalableAssignmentTaskTheme {
        RepoCommitsScreen(
            uiState = RepoCommitsUiState(
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
            repositoryName = "ScalableAssignmentTask",
            onBack = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
private fun RepoCommitsScreenEmptyPreview() {
    ScalableAssignmentTaskTheme {
        RepoCommitsScreen(
            uiState = RepoCommitsUiState(),
            repositoryName = "ScalableAssignmentTask",
            onBack = {},
        )
    }
}
