package com.kamilkomnacki.scalableassignmenttask.data.model

import java.time.LocalDate

data class Commit(
    val sha: String,
    val title: String,
    val author: String?,
    val date: LocalDate,
) {
    val shortSha: String
        get() = sha.take(7)
}
