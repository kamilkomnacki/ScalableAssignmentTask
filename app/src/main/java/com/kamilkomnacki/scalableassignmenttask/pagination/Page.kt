package com.kamilkomnacki.scalableassignmenttask.pagination

/** One downloaded page. [nextCursor] is null only on the last page. */
data class Page<out T>(val items: List<T>, val nextCursor: String?)
