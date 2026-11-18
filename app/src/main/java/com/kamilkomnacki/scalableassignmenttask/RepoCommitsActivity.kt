package com.kamilkomnacki.scalableassignmenttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.kamilkomnacki.scalableassignmenttask.ui.repoCommits.RepoCommitsScreen
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableColors

class RepoCommitsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repositoryName = requireNotNull(
            intent.getStringExtra(EXTRA_REPOSITORY_NAME)?.takeIf { it.isNotBlank() },
        ) { "Repository name is required for commits list screen." }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(ScalableColors.background.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(ScalableColors.background.toArgb())
        )
        setContent {
            ScalableAssignmentTaskTheme {
                RepoCommitsScreen(
                    repositoryName = repositoryName,
                    onBack = { onBackPressedDispatcher.onBackPressed() },
                )
            }
        }
    }

    companion object {
        const val EXTRA_REPOSITORY_NAME = "repository_name"
    }
}
