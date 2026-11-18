package com.kamilkomnacki.scalableassignmenttask.ui.pagination

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

/** Reports the last visible loaded item; a footer after the [itemCount] items counts as the last item. */
@Composable
fun LastVisibleItemEffect(
    listState: LazyListState,
    itemCount: Int,
    onItemVisible: (Int) -> Unit
) {
    val currentItemCount by rememberUpdatedState(itemCount)
    val currentOnItemVisible by rememberUpdatedState(onItemVisible)
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo
                .lastOrNull()
                ?.index
                ?.coerceAtMost(currentItemCount - 1)
        }
            .filterNotNull()
            .filter { it >= 0 }
            .collect { currentOnItemVisible(it) }
    }
}
