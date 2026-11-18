package com.kamilkomnacki.scalableassignmenttask.data.remote

import okhttp3.Headers
import okhttp3.Headers.Companion.headersOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NextLinkTest {
    @Test
    fun returnsNextUrlOnFirstPage() {
        val headers = linkHeader("""<${page(2)}>; rel="next", <${page(9)}>; rel="last"""")

        assertEquals(page(2), nextLink(headers))
    }

    @Test
    fun returnsNextUrlWhenEarlierEntryIsNotNext() {
        val headers = linkHeader(
            """<${page(4)}>; rel="prev", <${page(6)}>; rel="next", """ +
                """<${page(9)}>; rel="last", <${page(1)}>; rel="first""""
        )

        assertEquals(page(6), nextLink(headers))
    }

    @Test
    fun returnsNullOnLastPage() {
        val headers = linkHeader("""<${page(8)}>; rel="prev", <${page(1)}>; rel="first"""")

        assertNull(nextLink(headers))
    }

    @Test
    fun returnsNullWithoutLinkHeader() {
        assertNull(nextLink(headersOf("Content-Type", "application/json")))
    }

    @Test
    fun keepsCommaInsideUrl() {
        val url = "https://api.github.com/repositories/1/commits?path=a,b&per_page=100&page=2"
        val headers = linkHeader("""<$url>; rel="next", <${page(9)}>; rel="last"""")

        assertEquals(url, nextLink(headers))
    }

    @Test
    fun acceptsNoSpaceBeforeRel() {
        val headers = linkHeader("""<${page(2)}>;rel="next"""")

        assertEquals(page(2), nextLink(headers))
    }

    @Test
    fun ignoresEmptyUrl() {
        val headers = linkHeader("""<>; rel="next"""")

        assertNull(nextLink(headers))
    }

    @Test
    fun readsNextFromLaterLinkHeader() {
        val headers = headersOf(
            "Link", """<${page(1)}>; rel="first"""",
            "Link", """<${page(3)}>; rel="next"""",
        )

        assertEquals(page(3), nextLink(headers))
    }

    private fun linkHeader(value: String): Headers = headersOf("Link", value)

    private fun page(number: Int) =
        "https://api.github.com/user/123/repos?sort=full_name&direction=asc&per_page=100&page=$number"
}
