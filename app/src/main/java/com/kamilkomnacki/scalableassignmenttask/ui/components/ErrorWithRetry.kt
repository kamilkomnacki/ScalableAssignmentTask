package com.kamilkomnacki.scalableassignmenttask.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamilkomnacki.scalableassignmenttask.R
import com.kamilkomnacki.scalableassignmenttask.ui.theme.ScalableAssignmentTaskTheme

@Composable
fun ErrorWithRetry(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
        )
        Button(onClick = onRetryClick) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorWithRetryPreview() {
    ScalableAssignmentTaskTheme {
        ErrorWithRetry(
            message = stringResource(R.string.network_failure),
            onRetryClick = {},
        )
    }
}
