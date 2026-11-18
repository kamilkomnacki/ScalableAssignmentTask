package com.kamilkomnacki.scalableassignmenttask.ui.repoCommits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamilkomnacki.scalableassignmenttask.R
import com.kamilkomnacki.scalableassignmenttask.data.model.RepoCommit
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun RepoCommitRow(
    commit: RepoCommit,
    modifier: Modifier = Modifier,
) {
    val locale = LocalConfiguration.current.locales[0]
    val formatter = remember(locale) {
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale)
    }

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = commit.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = commit.author ?: stringResource(R.string.unknown_author),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = stringResource(
                    R.string.commit_metadata,
                    formatter.format(commit.date),
                    commit.shortSha,
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun RepoCommitRowPreview() {
    ScalableAssignmentTaskTheme {
        RepoCommitRow(
            commit = RepoCommit(
                sha = "a1b2c3d4e5f67890123456789012345678901234",
                title = "Add repository commit details",
                author = "Kamil Komnacki",
                date = LocalDate.of(2026, 11, 18),
            ),
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun RepoCommitRowLongTitleWithoutAuthorPreview() {
    ScalableAssignmentTaskTheme {
        RepoCommitRow(
            commit = RepoCommit(
                sha = "a1b2c3d4e5f67890123456789012345678901234",
                title = "Display commit details with a very long title that should be limited " +
                    "to two lines and truncated with an ellipsis",
                author = null,
                date = LocalDate.of(2026, 11, 18),
            ),
        )
    }
}
