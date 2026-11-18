package com.kamilkomnacki.scalableassignmenttask.pagination

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PrefetchThresholdTest {
    @Test
    fun isNotNearEndWhenPrefetchDistanceItemsFollow() {
        assertFalse(isNearEnd(lastIndex = 99, lastVisibleIndex = 79, prefetchDistance = 20))
    }

    @Test
    fun isNearEndWhenFewerThanPrefetchDistanceItemsFollow() {
        assertTrue(isNearEnd(lastIndex = 99, lastVisibleIndex = 80, prefetchDistance = 20))
    }

    @Test
    fun isNearEndOnFirstItemWhenListIsShorterThanPrefetchDistance() {
        assertTrue(isNearEnd(lastIndex = 9, lastVisibleIndex = 0, prefetchDistance = 20))
    }

    @Test
    fun onlyLastItemIsNearEndWithPrefetchDistanceOne() {
        assertFalse(isNearEnd(lastIndex = 99, lastVisibleIndex = 98, prefetchDistance = 1))
        assertTrue(isNearEnd(lastIndex = 99, lastVisibleIndex = 99, prefetchDistance = 1))
    }

    @Test
    fun returnsFirstIndexNearEnd() {
        assertEquals(80, firstIndexNearEnd(lastIndex = 99, prefetchDistance = 20))
    }
}
