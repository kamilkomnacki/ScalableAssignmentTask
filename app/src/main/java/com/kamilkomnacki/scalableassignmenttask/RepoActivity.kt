package com.kamilkomnacki.scalableassignmenttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.kamilkomnacki.scalableassignmenttask.ui.repoList.RepoListScreen
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableColors

class RepoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(ScalableColors.background.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(ScalableColors.background.toArgb())
        )
        setContent {
            ScalableAssignmentTaskTheme {
                RepoListScreen()
            }
        }
    }
}
