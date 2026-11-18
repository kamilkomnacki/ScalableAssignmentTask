package com.kamilkomnacki.scalableassignmenttask.pagination

/** True when fewer than [prefetchDistance] loaded items follow the last visible one. */
fun isNearEnd(lastIndex: Int, lastVisibleIndex: Int, prefetchDistance: Int): Boolean =
    lastIndex - lastVisibleIndex < prefetchDistance

/** The lowest visible index for which [isNearEnd] is true. */
fun firstIndexNearEnd(lastIndex: Int, prefetchDistance: Int): Int =
    lastIndex - prefetchDistance + 1
