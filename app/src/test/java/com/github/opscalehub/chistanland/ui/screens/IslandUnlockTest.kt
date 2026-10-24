package com.github.opscalehub.chistanland.ui.screens

import com.github.opscalehub.chistanland.data.LearningItem
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IslandUnlockTest {

    private fun item(id: Int, level: Int = 1, attempted: Boolean = false) = LearningItem(
        id = "a$id", character = "ا", word = "", phonetic = "", imageUrl = "",
        category = "ALPHABET", level = level, lastReviewTime = if (attempted) 1L else 0L
    )

    private fun items(vararg levels: Int) = levels.mapIndexed { i, l -> item(i, l) }

    @Test
    fun firstIslandIsAlwaysOpen() {
        assertFalse(IslandUnlock.isIslandLocked(items(1, 1, 1), 0))
    }

    @Test
    fun nextIslandLockedBeforeSuccess() {
        assertTrue(IslandUnlock.isIslandLocked(items(1, 1, 1), 1))
    }

    @Test
    fun failedAttemptDoesNotUnlockNextIsland() {
        val list = listOf(item(0, level = 1, attempted = true), item(1))
        assertTrue(IslandUnlock.isIslandLocked(list, 1))
    }

    @Test
    fun oneSuccessfulSessionUnlocksNextIsland() {
        val list = items(2, 1, 1)
        assertFalse(IslandUnlock.isIslandLocked(list, 1))
        assertTrue(IslandUnlock.isIslandLocked(list, 2))
    }

    @Test
    fun fullMasteryIsNotRequired() {
        assertFalse(IslandUnlock.isIslandLocked(items(2, 3, 1), 2))
    }

    @Test
    fun reviewParkAppearsAfterEveryThirdIslandButNotAfterLast() {
        val list = items(1, 1, 1, 1, 1, 1)
        assertFalse(IslandUnlock.hasReviewParkAfter(list, 1))
        assertTrue(IslandUnlock.hasReviewParkAfter(list, 2))
        assertFalse(IslandUnlock.hasReviewParkAfter(list, 5))
    }

    @Test
    fun reviewParkLockedUntilAllPrecedingCompleted() {
        assertTrue(IslandUnlock.isReviewParkLocked(items(2, 2, 1, 1), 2))
        assertFalse(IslandUnlock.isReviewParkLocked(items(2, 2, 2, 1), 2))
    }
}
