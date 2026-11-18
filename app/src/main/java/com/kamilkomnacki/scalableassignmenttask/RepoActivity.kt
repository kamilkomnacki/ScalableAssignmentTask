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
import com.kamilkomnacki.scalableassignmenttask.ui.repoList.RepoListScreen
import com.kamilkomnacki.scalableassignmenttask.ui.repoList.RepoListViewModel
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableColors

class RepoActivity : ComponentActivity() {
    private val viewModel: RepoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(ScalableColors.background.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(ScalableColors.background.toArgb())
        )
        setContent {
            ScalableAssignmentTaskTheme {
                val uiState by viewModel.uiState.collectAsState()
                RepoListScreen(uiState = uiState)
            }
        }
    }
}
