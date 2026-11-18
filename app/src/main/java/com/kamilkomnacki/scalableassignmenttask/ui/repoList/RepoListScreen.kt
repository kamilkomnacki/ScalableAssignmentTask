package com.kamilkomnacki.scalableassignmenttask.ui.repoList

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.kamilkomnacki.scalableassignmenttask.R
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo
import com.kamilkomnacki.scalableassignmenttask.ui.repoList.preview.RepoListPreviewParameterProvider
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListScreen(
    uiState: RepoListUiState,
    account: String,
    modifier: Modifier = Modifier,
    onRepoClick: (Repo) -> Unit = {},
    onRetryClick: () -> Unit = {},
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
                is RepoListUiState.Error -> Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = stringResource(
                            when (uiState.error) {
                                RepoListError.Network -> R.string.network_failure
                                RepoListError.Generic -> R.string.repo_list_failure
                            },
                        ),
                        textAlign = TextAlign.Center,
                    )
                    Button(onClick = onRetryClick) {
                        Text(stringResource(R.string.retry))
                    }
                }
                is RepoListUiState.Success -> if (uiState.repositories.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_public_repos),
                        modifier = Modifier.padding(16.dp),
                    )
                } else {
                    LazyColumn(
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
                    }
                }
            }
        }
    }
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
        )
    }
}
