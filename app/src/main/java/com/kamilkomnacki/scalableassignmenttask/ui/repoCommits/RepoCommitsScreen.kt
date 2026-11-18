package com.kamilkomnacki.scalableassignmenttask.ui.repoCommits

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.kamilkomnacki.scalableassignmenttask.R
import com.kamilkomnacki.scalableassignmenttask.pagination.LastVisibleItemEffect
import com.kamilkomnacki.scalableassignmenttask.pagination.ListFooter
import com.kamilkomnacki.scalableassignmenttask.ui.components.ErrorWithRetry
import com.kamilkomnacki.scalableassignmenttask.ui.components.LoadingFooter
import com.kamilkomnacki.scalableassignmenttask.ui.repoCommits.preview.RepoCommitsPreviewParameterProvider
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme

private const val FOOTER_KEY = "footer"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoCommitsScreen(
    uiState: RepoCommitsUiState,
    repoName: String,
    onBack: () -> Unit,
    onRetryClick: () -> Unit,
    onItemVisible: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { TopBarTitle(repoName) },
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
                is RepoCommitsUiState.Error -> ErrorWithRetry(
                    message = stringResource(uiState.error.messageRes()),
                    onRetryClick = onRetryClick,
                )
                is RepoCommitsUiState.Success -> RepoCommitsListContent(
                    uiState = uiState,
                    onRetryClick = onRetryClick,
                    onItemVisible = onItemVisible,
                )
            }
        }
    }
}

@Composable
private fun TopBarTitle(repoName: String) {
    Text(
        text = stringResource(R.string.repo_commits_title_with_name, repoName),
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
private fun RepoCommitsListContent(
    uiState: RepoCommitsUiState.Success,
    onRetryClick: () -> Unit,
    onItemVisible: (Int) -> Unit,
) {
    if (uiState.commits.isEmpty()) {
        Text(
            text = stringResource(R.string.no_commits),
            modifier = Modifier.padding(16.dp),
        )
    } else {
        val listState = rememberLazyListState()
        LastVisibleItemEffect(listState, uiState.commits.size, onItemVisible)
        LazyColumn(
            state = listState,
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
            footerItem(uiState.footer, onRetryClick)
        }
    }
}

private fun LazyListScope.footerItem(footer: ListFooter<RepoCommitsError>, onRetryClick: () -> Unit) {
    when (footer) {
        ListFooter.None -> Unit
        ListFooter.Loading -> item(key = FOOTER_KEY) { LoadingFooter() }
        is ListFooter.Error -> item(key = FOOTER_KEY) {
            ErrorWithRetry(
                message = stringResource(footer.error.olderHistoryMessageRes()),
                onRetryClick = onRetryClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

/** Shown on the whole screen when no commits are loaded yet. */
@StringRes
private fun RepoCommitsError.messageRes(): Int = when (this) {
    RepoCommitsError.Network -> R.string.network_failure
    RepoCommitsError.RateLimited -> R.string.rate_limit_failure
    RepoCommitsError.Generic -> R.string.commits_failure
}

/** Shown below the loaded commits when an older page fails. */
@StringRes
private fun RepoCommitsError.olderHistoryMessageRes(): Int = when (this) {
    RepoCommitsError.Network -> R.string.network_failure
    RepoCommitsError.RateLimited -> R.string.rate_limit_failure
    RepoCommitsError.Generic -> R.string.older_history_failure
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
private fun RepoCommitsScreenPreview(
    @PreviewParameter(RepoCommitsPreviewParameterProvider::class) uiState: RepoCommitsUiState,
) {
    ScalableAssignmentTaskTheme {
        RepoCommitsScreen(
            uiState = uiState,
            repoName = RepoCommitsPreviewParameterProvider.REPOSITORY_NAME,
            onBack = {},
            onRetryClick = {},
            onItemVisible = {},
        )
    }
}
