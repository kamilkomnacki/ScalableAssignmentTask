package com.kamilkomnacki.scalableassignmenttask.pagination

import androidx.compose.runtime.Immutable

/** What a list shows after its loaded items; [E] is the screen's error enum. */
@Immutable
sealed interface ListFooter<out E> {
    data object None : ListFooter<Nothing>
    data object Loading : ListFooter<Nothing>
    data class Error<out E>(val error: E) : ListFooter<E>
}

fun <E> LoadStatus.toFooter(mapError: (Exception) -> E): ListFooter<E> =
    when (this) {
        LoadStatus.Idle -> ListFooter.None
        LoadStatus.Loading -> ListFooter.Loading
        is LoadStatus.Failed -> ListFooter.Error(mapError(error))
    }
