package com.kamilkomnacki.scalableassignmenttask.ui.repoList.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.kamilkomnacki.scalableassignmenttask.data.model.Repo

class RepoRowPreviewParameterProvider : PreviewParameterProvider<Repo> {
    override val values: Sequence<Repo> = sequenceOf(
        Repo(
            id = 1L,
            name = "ScalableAssignmentTask",
            description = "An Android app for browsing GitHub repositories and commits.",
            technology = "Kotlin",
            stars = 42,
        ),
        Repo(
            id = 2L,
            name = "Long technology name",
            description = "The technology should end before the stars.",
            technology = "A very long technology name that should be truncated with an ellipsis",
            stars = 123456,
        ),
        Repo(
            id = 3L,
            name = "android-notes",
            description = null,
            technology = null,
            stars = 0,
        ),
    )

    override fun getDisplayName(index: Int): String = values.elementAt(index).name
}
