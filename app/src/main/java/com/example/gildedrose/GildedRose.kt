package com.example.gildedrose

import kotlin.math.min

class GildedRose(var items: Array<Item>) {

    fun updateItems() {
        items.forEach {
            updateSellin(it)
            updateQuality(it)
            //bop(it)
        }
    }

    private fun updateSellin(item: Item) {
        if (!item.name.contains("Sulfuras")) {
            item.sellIn--
        }
    }

    private fun getQualityDecay(item: Item): Int {
        if (item.name.contains("Aged Brie")) {
            return -1
        } else if (item.name.contains("Backstage passes")) {
            return when {
                (item.sellIn <= 0) -> item.quality //expired ticket, quality>=0 so doubling won't affect this
                (item.sellIn < 5) -> -3
                (item.sellIn < 10) -> -2
                else -> -1
            }
        } else {
            return 1
        }
    }

    private fun updateQuality(item: Item) {
        if (item.name.contains("Sulfuras")) {
            return
        }
        var qualityDecay = getQualityDecay(item)
        if (item.sellIn <= 0) {
            qualityDecay *= 2
        }
        if (item.name.contains("Conjured")) {
            qualityDecay *= 2
        }
        item.quality -= qualityDecay
        //clamp quality
        item.quality = min(50,kotlin.math.max(item.quality,0)) //apparently max is ambiguous???
    }


}

