package com.github.opscalehub.chistanland.ui.screens

import com.github.opscalehub.chistanland.data.LearningItem

// قوانین باز شدن جزیره‌ها: یک آیتم وقتی «با موفقیت طی شده» که حداقل یک جلسه موفق داشته باشد (level > 1)
object IslandUnlock {
    const val REVIEW_GROUP_SIZE = 3

    fun isCompleted(item: LearningItem): Boolean = item.level > 1

    // جزیره اول همیشه باز است؛ بقیه بعد از طی شدن موفق جزیره قبلی باز می‌شوند
    fun isIslandLocked(items: List<LearningItem>, index: Int): Boolean =
        index > 0 && !isCompleted(items[index - 1])

    // شهربازی مرور بعد از هر ۳ جزیره نمایش داده می‌شود (نه بعد از آخرین جزیره)
    fun hasReviewParkAfter(items: List<LearningItem>, index: Int): Boolean =
        (index + 1) % REVIEW_GROUP_SIZE == 0 && index < items.size - 1

    // شهربازی زمانی باز می‌شود که تمام حروفِ قبل از آن با موفقیت طی شده باشند
    fun isReviewParkLocked(items: List<LearningItem>, index: Int): Boolean =
        items.take(index + 1).any { !isCompleted(it) }
}
