package com.kamilkomnacki.scalableassignmenttask.pagination

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "Paginator"

/**
 * Forward-only cursor pagination for one list session.
 *
 * - The first page loads right away with a null cursor.
 * - The next page loads when [onItemVisible] reports an item closer than [prefetchDistance]
 *   to the end. After every successful page the threshold is checked again.
 * - Only one page loads at a time.
 * - A failed page keeps the loaded items and waits for [retry], which repeats the same cursor.
 * - Only a successful page without a next cursor ends the list.
 *
 * Thread-confined: use a single-threaded scope (viewModelScope) and call it from the main thread.
 * Each load decision is logged with `Log.d` under the "Paginator" tag.
 */
class Paginator<T>(
    private val scope: CoroutineScope,
    private val prefetchDistance: Int,
    private val loadPage: suspend (cursor: String?) -> Page<T>,
) {
    private val mutableState = MutableStateFlow(PaginationState<T>())
    val state: StateFlow<PaginationState<T>> = mutableState.asStateFlow()

    private var nextCursor: String? = null
    private var lastVisibleIndex = -1
    private var loadJob: Job? = null

    init {
        require(prefetchDistance > 0) { "Prefetch distance for paginator must be positive" }
        load()
    }

    fun onItemVisible(index: Int) {
        lastVisibleIndex = index
        if (mutableState.value.status == LoadStatus.Idle && needsMore()) {
            Log.d(TAG, "Item $index is visible, so the next page is needed")
            load()
        }
    }

    fun retry() {
        if (mutableState.value.status is LoadStatus.Failed) {
            Log.d(TAG, "Retrying the failed page")
            load()
        }
    }

    private fun needsMore(): Boolean {
        val current = mutableState.value
        return !current.endReached &&
            (current.items.isEmpty() || isNearEnd(current.items.lastIndex, lastVisibleIndex, prefetchDistance))
    }

    private fun load() {
        if (loadJob?.isActive == true) return
        mutableState.update { it.copy(status = LoadStatus.Loading) }
        loadJob = scope.launch {
            do {
                Log.d(TAG, "Loading ${nextCursor ?: "the first page"}")
                val page = try {
                    loadPage(nextCursor)
                } catch (cancelled: CancellationException) {
                    Log.d(TAG, "Loading cancelled")
                    throw cancelled
                } catch (failure: Exception) {
                    Log.d(TAG, "Loading failed, waiting for retry: $failure")
                    mutableState.update { it.copy(status = LoadStatus.Failed(failure)) }
                    return@launch
                }
                append(page)
            } while (needsMore())
            mutableState.update { it.copy(status = LoadStatus.Idle) }
            logIdle()
        }
    }

    private fun append(page: Page<T>) {
        nextCursor = page.nextCursor
        mutableState.update {
            it.copy(
                items = it.items.addingAll(page.items),
                endReached = page.nextCursor == null
            )
        }
        Log.d(TAG, "Loaded ${page.items.size} items, ${mutableState.value.items.size} in total")
    }

    private fun logIdle() {
        val current = mutableState.value
        if (current.endReached) {
            Log.d(TAG, "End of list, ${current.items.size} items")
        } else {
            Log.d(TAG, "Waiting until item ${firstIndexNearEnd(current.items.lastIndex, prefetchDistance)} is visible")
        }
    }
}
