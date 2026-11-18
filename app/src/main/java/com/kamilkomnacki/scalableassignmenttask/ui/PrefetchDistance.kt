package com.kamilkomnacki.scalableassignmenttask.ui

// Both lists load 100 items per page (`per_page` in GitHubApi), so the next page starts loading
// when 20 items are left, a fifth of a page.
const val PREFETCH_DISTANCE = 20
