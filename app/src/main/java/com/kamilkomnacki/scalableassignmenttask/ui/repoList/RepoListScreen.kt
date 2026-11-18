package com.kamilkomnacki.scalableassignmenttask.ui.repoList

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.kamilkomnacki.scalableassignmenttask.R
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo
import com.kamilkomnacki.scalableassignmenttask.pagination.LastVisibleItemEffect
import com.kamilkomnacki.scalableassignmenttask.pagination.ListFooter
import com.kamilkomnacki.scalableassignmenttask.ui.components.ErrorWithRetry
import com.kamilkomnacki.scalableassignmenttask.ui.components.LoadingFooter
import com.kamilkomnacki.scalableassignmenttask.ui.repoList.preview.RepoListPreviewParameterProvider
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme

private const val FOOTER_KEY = "footer"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListScreen(
    uiState: RepoListUiState,
    account: String,
    onRepoClick: (Repo) -> Unit,
    onRetryClick: () -> Unit,
    onItemVisible: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.repo_list_title, account),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
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
            when (uiState) {
                RepoListUiState.Loading -> CircularProgressIndicator()
                is RepoListUiState.Error -> ErrorWithRetry(
                    message = stringResource(uiState.error.messageRes()),
                    onRetryClick = onRetryClick,
                )
                is RepoListUiState.Success -> RepoListContent(
                    uiState = uiState,
                    onRepoClick = onRepoClick,
                    onRetryClick = onRetryClick,
                    onItemVisible = onItemVisible,
                )
            }
        }
    }
}

@Composable
private fun RepoListContent(
    uiState: RepoListUiState.Success,
    onRepoClick: (Repo) -> Unit,
    onRetryClick: () -> Unit,
    onItemVisible: (Int) -> Unit,
) {
    if (uiState.repositories.isEmpty()) {
        Text(
            text = stringResource(R.string.no_public_repos),
            modifier = Modifier.padding(16.dp),
        )
    } else {
        val listState = rememberLazyListState()
        LastVisibleItemEffect(listState, uiState.repositories.size, onItemVisible)
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
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
            footerItem(uiState.footer, onRetryClick)
        }
    }
}

private fun LazyListScope.footerItem(footer: ListFooter<RepoListError>, onRetryClick: () -> Unit) {
    when (footer) {
        ListFooter.None -> Unit
        ListFooter.Loading -> item(key = FOOTER_KEY) { LoadingFooter() }
        is ListFooter.Error -> item(key = FOOTER_KEY) {
            ErrorWithRetry(
                message = stringResource(footer.error.messageRes()),
                onRetryClick = onRetryClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@StringRes
private fun RepoListError.messageRes(): Int = when (this) {
    RepoListError.Network -> R.string.network_failure
    RepoListError.RateLimited -> R.string.rate_limit_failure
    RepoListError.Generic -> R.string.repo_list_failure
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
private fun RepoListScreenPreview(
    @PreviewParameter(RepoListPreviewParameterProvider::class) uiState: RepoListUiState,
) {
    ScalableAssignmentTaskTheme {
        RepoListScreen(
            account = RepoListPreviewParameterProvider.ACCOUNT,
            uiState = uiState,
            onRepoClick = {},
            onRetryClick = {},
            onItemVisible = {},
        )
    }
}
