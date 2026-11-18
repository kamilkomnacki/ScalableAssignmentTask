package com.kamilkomnacki.scalableassignmenttask.ui.repoList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RepoListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        RepoListUiState(
            repositories = listOf(
                Repo(
                    id = 1L,
                    name = "kotlin",
                    owner = "JetBrains",
                    description = "The Kotlin programming language.",
                    technology = "Kotlin",
                    stars = 1200,
                    defaultBranch = "master",
                ),
                Repo(
                    id = 2L,
                    name = "kotlinx.coroutines",
                    owner = "Kotlin",
                    description = "Support for asynchronous programming with Kotlin coroutines.",
                    technology = "Kotlin",
                    stars = 850,
                    defaultBranch = "master",
                ),
                Repo(
                    id = 3L,
                    name = "compose-samples",
                    owner = "android",
                    description = "Sample Android apps built with Jetpack Compose.",
                    technology = "Kotlin",
                    stars = 640,
                    defaultBranch = "main",
                ),
                Repo(
                    id = 4L,
                    name = "sqldelight",
                    owner = "sqldelight",
                    description = "Generate Kotlin APIs from SQL statements.",
                    technology = "Kotlin",
                    stars = 420,
                    defaultBranch = "master",
                ),
                Repo(
                    id = 5L,
                    name = "okhttp",
                    owner = "square",
                    description = "An HTTP client for Android and the JVM.",
                    technology = "Java",
                    stars = 980,
                    defaultBranch = "master",
                ),
                Repo(
                    id = 6L,
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
    val uiState = _uiState.asStateFlow()
}
