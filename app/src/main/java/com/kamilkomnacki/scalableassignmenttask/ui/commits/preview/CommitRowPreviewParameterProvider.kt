package com.kamilkomnacki.scalableassignmenttask.ui.commits.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.kamilkomnacki.scalableassignmenttask.data.model.Commit
import java.time.LocalDate

class CommitRowPreviewParameterProvider : PreviewParameterProvider<Commit> {
    override val values: Sequence<Commit> = sequenceOf(
        Commit(
            sha = "a1b2c3d4e5f67890123456789012345678901234",
            title = "Add repository commit details",
            author = "Kamil Komnacki",
            date = LocalDate.of(2026, 11, 18),
        ),
        Commit(
            sha = "a1b2c3d4e5f67890123456789012345678901234",
            title = "Display commit details with a very long title that should be limited " +
                "to two lines and truncated with an ellipsis",
            author = null,
            date = LocalDate.of(2026, 11, 18),
        ),
    )

    override fun getDisplayName(index: Int): String =
        if (values.elementAt(index).author == null) "Long title without author" else "Commit"
}
