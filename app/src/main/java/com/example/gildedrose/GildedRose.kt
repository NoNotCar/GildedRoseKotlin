package com.example.gildedrose

class GildedRose(var items: Array<Item>) {
    fun updateSellin(item: Item) {
        if (!item.name.contains("Sulfuras")) {
            item.sellIn--
        }
    }

    fun getQualityDecay(item: Item): Int {
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

    fun updateQuality(item: Item) {
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
        item.quality = when {
            (item.quality < 0) -> 0
            (item.quality > 50) -> 50
            else -> item.quality
        }
    }

    fun updateItems() {
        items.forEach {
            updateSellin(it)
            updateQuality(it)
            //bop(it)
        }
    }

}

