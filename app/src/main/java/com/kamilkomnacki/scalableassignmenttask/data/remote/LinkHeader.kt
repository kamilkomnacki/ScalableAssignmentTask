package com.kamilkomnacki.scalableassignmenttask.data.remote

import okhttp3.Headers
import okhttp3.HttpUrl

/**
 * Returns the URL of the next page, or null on the last page. The paginator uses it as its cursor.
 *
 * GitHub sends this URL only in the `Link` header, as one of several entries:
 * `<https://api.github.com/...&page=2>; rel="next", <https://api.github.com/...&page=9>; rel="last"`.
 * [NEXT_PAGE_LINK] takes the URL in `<...>` followed by `rel="next"`. `[^>]` ends the URL at its own `>`,
 * so one match never spans two entries. The header isn't split on commas, because a URL can contain one.
 */
fun nextLink(headers: Headers): String? =
    headers.values("Link").firstNotNullOfOrNull { value -> NEXT_PAGE_LINK.find(value)?.groupValues?.get(1) }

private val NEXT_PAGE_LINK = Regex("""<([^>]+)>;\s*rel="next"""")

/**
 * Whether a [nextLink] URL may be requested. Retrofit's `@Url` replaces the base URL with an absolute
 * one, so the host of every later page comes from a response header instead of the code.
 * GitHubRepository follows a next link only when it points at the GitHub API origin: https,
 * `api.github.com`, the default port and no user info.
 */
fun HttpUrl.isGitHubApi() =
    scheme == "https" &&
            host == "api.github.com" &&
            port == 443 &&
            username.isEmpty() &&
            password.isEmpty()
