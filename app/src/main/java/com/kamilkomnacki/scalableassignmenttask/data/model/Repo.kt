package com.kamilkomnacki.scalableassignmenttask.data.model

data class Repo(
    val id: Long,
    val name: String,
    val description: String?,
    val technology: String?,
    val stars: Int,
)
