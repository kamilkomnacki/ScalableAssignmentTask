package com.kamilkomnacki.scalableassignmenttask.ui.repoCommits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.kamilkomnacki.scalableassignmenttask.R
import com.kamilkomnacki.scalableassignmenttask.ui.repoCommits.preview.RepoCommitsPreviewParameterProvider
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoCommitsScreen(
    uiState: RepoCommitsUiState,
    repositoryName: String,
    onBack: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { TopBarTitle(repositoryName) },
                navigationIcon = { BackIcon(onBack) },
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
            when (uiState) {
                RepoCommitsUiState.Loading -> CircularProgressIndicator()
                is RepoCommitsUiState.Error -> RepoCommitsErrorPanel(uiState, onRetryClick)
                is RepoCommitsUiState.Success -> RepoCommitsListContent(uiState)
            }
        }
    }
}

@Composable
private fun TopBarTitle(repositoryName: String) {
    Text(
        text = stringResource(R.string.repo_commits_title_with_name, repositoryName),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun BackIcon(onBack: () -> Unit) {
    IconButton(onClick = onBack) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = stringResource(R.string.back),
        )
    }
}

@Composable
private fun RepoCommitsListContent(uiState: RepoCommitsUiState.Success) {
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

@Composable
private fun RepoCommitsErrorPanel(
    uiState: RepoCommitsUiState.Error,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = stringResource(
                when (uiState.error) {
                    RepoCommitsError.Network -> R.string.network_failure
                    RepoCommitsError.RateLimited -> R.string.rate_limit_failure
                    RepoCommitsError.TokenRejected -> R.string.token_rejected
                    RepoCommitsError.Generic -> R.string.commits_failure
                },
            ),
            textAlign = TextAlign.Center,
        )
        Button(onClick = onRetryClick) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
private fun RepoCommitsScreenPreview(
    @PreviewParameter(RepoCommitsPreviewParameterProvider::class) uiState: RepoCommitsUiState,
) {
    ScalableAssignmentTaskTheme {
        RepoCommitsScreen(
            uiState = uiState,
            repositoryName = RepoCommitsPreviewParameterProvider.REPOSITORY_NAME,
            onBack = {},
            onRetryClick = {},
        )
    }
}
