package com.example.gildedrose

import org.junit.Assert.*
import org.junit.Test

class GildedRoseTest {
    fun single_item_setup(i: Item): GildedRose {
        return GildedRose(arrayOf(i))
    }

    fun update_item_once(i: Item): Item {
        val app = single_item_setup(i)
        app.updateItems()
        return app.items[0]
    }

    @Test
    fun basic_items_quality_falls_1_per_day() {
        val item = update_item_once(Item("spam", 10, 5))
        assertEquals(4, item.quality)
    }

    @Test
    fun basic_items_name_is_unchanged() {
        val item = update_item_once(Item("spam", 10, 5))
        assertEquals("spam", item.name)
    }

    @Test
    fun basic_items_sellin_falls_1_per_day() {
        val item = update_item_once(Item("spam", 10, 5))
        assertEquals(9, item.sellIn)

    }

    @Test
    fun expired_items_quality_falls_by_2() {
        val item = update_item_once(Item("dodgy milk", 0, 5))
        assertEquals(3, item.quality)
    }

    @Test
    fun quality_doesnt_exceed_50() {
        val item = update_item_once(Item("Aged Brie", 10, 50))
        assertEquals(50, item.quality)
    }

    @Test
    fun quality_is_never_negative() {
        val item = update_item_once(Item("pile of rust", 0, 0))
        assertEquals(0, item.quality)

    }

    @Test
    fun quality_is_never_negative_for_conjured_expired_items() {

        val item = update_item_once(Item("Conjured something", 0, 1))
        assertEquals(0, item.quality)
    }

    @Test
    fun brie_quality_increases_by_1() {
        val item = update_item_once(Item("Aged Brie", 10, 30))
        assertEquals(31, item.quality)
    }

    @Test
    fun sulfuras_quality_unchanged() {
        val item = update_item_once(Item("Sulfuras, Hand of Ragnaros", 10, 30))
        assertEquals(30, item.quality)
    }

    @Test
    fun sulfuras_sellin_unchanged() {
        val item = update_item_once(Item("Sulfuras, Hand of Ragnaros", 10, 30))
        assertEquals(10, item.sellIn)
    }

    @Test
    fun backstage_passes_up_by_1() {
        val item = update_item_once(Item("Backstage passes to a concert", 11, 30))
        assertEquals(31, item.quality)
    }

    @Test
    fun backstage_passes_up_by_2_between_10_and_6_days() {
        val item = update_item_once(Item("Backstage passes to a concert", 10, 30))
        assertEquals(32, item.quality)
        val item2 = update_item_once(Item("Backstage passes to a concert", 6, 30))
        assertEquals(32, item2.quality)

    }

    @Test
    fun backstage_passes_up_by_3_with_time_between_5_and_1_days() {
        val item = update_item_once(Item("Backstage passes to a concert", 5, 30))
        assertEquals(33, item.quality)
        val item2 = update_item_once(Item("Backstage passes to a concert", 1, 33))
        assertEquals(36, item2.quality)
    }

    @Test
    fun backstage_passes_are_worthless_if_expired() {
        val item = Item("Backstage passes to a concert", 0, 30)
        update_item_once(item)
        assertEquals(0, item.quality)
        update_item_once(item)
        assertEquals(0, item.quality)
    }

    @Test
    fun conjured_items_quality_falls_by_2() {
        val item = update_item_once(Item("Conjured spam", 10, 3))
        assertEquals(1, item.quality)
    }

    @Test
    fun conjured_items_sellin_falls_by_1() {
        val item = update_item_once(Item("Conjured spam", 10, 3))
        assertEquals(9, item.sellIn)
    }

    @Test
    fun conjured_items_quality_cannot_be_negative() {
        //Checks that ordinary "Conjured" items degrade 2 per day
        val item = update_item_once(Item("Conjured spam", 10, 1))

        assertEquals(0, item.quality)
    }

    @Test
    fun conjured_expired_quality_falls_4_per_day() {
        val item = update_item_once(Item("Conjured spam", 0, 7))
        assertEquals(3, item.quality)


    }
    @Test
    fun conjured_brie_increases_2_per_day() {
        val item = update_item_once(Item("Conjured Aged Brie", 2, 5))
        assertEquals(7, item.quality)
    }


    @Test
    fun conjured_expired_brie_increases_4_per_day() {
        val item = update_item_once(Item("Conjured Aged Brie", 0, 5))
        assertEquals(9, item.quality)
    }

    @Test
    fun conjured_passes_quality_increases_by_2() {
        val item = update_item_once(Item("Conjured Backstage passes", 11, 5))
        assertEquals(7, item.quality)
    }

    @Test
    fun conjured_passes_quality_increases_by_4_with_less_than_10_days() {
        val item = update_item_once(Item("Conjured Backstage passes", 10, 5))
        assertEquals(9, item.quality)
    }

    @Test
    fun conjured_sulfuras_doesnt_change_in_quality() {
        val item = update_item_once(Item("Conjured Sulfuras", 11, 5))
        assertEquals(5, item.quality)
    }
}


