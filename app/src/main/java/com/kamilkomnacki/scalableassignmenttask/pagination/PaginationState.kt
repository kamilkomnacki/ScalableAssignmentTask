package com.kamilkomnacki.scalableassignmenttask.pagination

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

sealed interface LoadStatus {
    /** No page is loading; the next one waits until the list scrolls near its end. */
    data object Idle : LoadStatus

    data object Loading : LoadStatus

    /** The last page failed; the loaded items stay and the page waits for a retry. */
    data class Failed(val error: Exception) : LoadStatus
}

/** Everything loaded so far, in page order. */
data class PaginationState<out T>(
    val items: PersistentList<T> = persistentListOf(),
    val status: LoadStatus = LoadStatus.Loading,
    val endReached: Boolean = false,
)
