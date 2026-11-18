package com.kamilkomnacki.scalableassignmenttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kamilkomnacki.scalableassignmenttask.ui.repoCommits.RepoCommitsScreen
import com.kamilkomnacki.scalableassignmenttask.ui.repoCommits.RepoCommitsViewModel
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableColors

class RepoCommitsActivity : ComponentActivity() {
    private val repositoryName: String by lazy {
        requireNotNull(
            intent.getStringExtra(EXTRA_REPOSITORY_NAME)?.takeIf { it.isNotBlank() },
        ) { "Repository name is required for commits list screen." }
    }

    private val viewModel: RepoCommitsViewModel by viewModels {
        viewModelFactory {
            initializer {
                RepoCommitsViewModel(
                    repository = (application as ScalableApplication).gitHubRepository,
                    repositoryName = repositoryName,
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(ScalableColors.background.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(ScalableColors.background.toArgb())
        )
        setContent {
            ScalableAssignmentTaskTheme {
                val uiState by viewModel.uiState.collectAsState()
                RepoCommitsScreen(
                    uiState = uiState,
                    repositoryName = repositoryName,
                    onBack = { onBackPressedDispatcher.onBackPressed() },
                    onRetryClick = viewModel::loadCommits,
                )
            }
        }
    }

    companion object {
        const val EXTRA_REPOSITORY_NAME = "repository_name"
    }
}
