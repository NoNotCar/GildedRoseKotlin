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
    fun basic_items_quality_falls_once_per_day() {
        val item = update_item_once(Item("spam", 10, 5))
        assertEquals("spam", item.name)
        assertEquals(4, item.quality)
    }

    @Test
    fun basic_items_sellin_falls_once_per_day() {
        val item = update_item_once(Item("spam", 10, 5))
        assertEquals("spam", item.name)
        assertEquals(9, item.sellIn)

    }

    @Test
    fun expired_items_degrade_twice_as_fast() {
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

        //Check this is still true for a "worst case" conjured, expired item
        val item2 = update_item_once(Item("Conjured something", 0, 1))
        assertEquals(0, item2.quality)
    }

    @Test
    fun brie_quality_increases_with_time() {
        val item = update_item_once(Item("Aged Brie", 10, 30))
        assertEquals(31, item.quality)
    }

    @Test
    fun sulfuras_is_legendary_and_so_does_not_degrade() {
        val item = update_item_once(Item("Sulfuras, Hand of Ragnaros", 10, 30))
        assertEquals(30, item.quality)
        assertEquals(10, item.sellIn)
    }

    @Test
    fun backstage_passes_up_by_1_with_time() {
        val item = update_item_once(Item("Backstage passes to a concert", 11, 30))
        assertEquals(31, item.quality)
    }

    @Test
    fun backstage_passes_up_by_2_with_time_between_10_and_6_days() {
        val item = update_item_once(Item("Backstage passes to a concert", 10, 30))
        assertEquals(32, item.quality)
        val item2 = update_item_once(Item("Backstage passes to a concert", 6, 30))
        assertEquals(32, item2.quality)

    }

    @Test
    fun backstage_passes_up_by_3_with_time_between_5_and_1_days() {
        val item = update_item_once(Item("Backstage passes to a concert", 5, 30))
        assertEquals(33, item.quality)
        val item2 = update_item_once(Item("Backstage passes to a concert", 2, 30))
        assertEquals(33, item2.quality)
    }

    @Test
    fun backstage_passes_are_worthless_if_expired() {
        val item = Item("Backstage passes to a concert", 1, 30)
        update_item_once(item)
        assertEquals(0, item.quality)
        update_item_once(item)
        assertEquals(0, item.quality)
    }

    @Test
    fun conjured_items_degrade_faster() {
        //Checks that ordinary "Conjured" items degrade 2 per day
        val item = Item("Conjured spam", 10, 3)
        val app = single_item_setup(item)
        app.updateItems()
        assertEquals("Conjured spam", item.name)
        assertEquals(9, item.sellIn)
        assertEquals(1, item.quality)
        //Checks that the quality can't go negative
        app.updateItems()
        assertEquals(0, item.quality)
    }

    @Test
    fun conjured_expired_degrade_at_4_per_day() {
        val item = update_item_once(Item("Conjured spam", 1, 7))
        assertEquals(3, item.quality)


    }

    @Test
    fun conjured_brie() {
        //Checks that Conjured brie increases by 2
        val brie = Item("Conjured Aged Brie", 2, 5)
        val app = single_item_setup(brie)
        app.updateItems()
        assertEquals(7, brie.quality)
        //Checks that the Conjured, expired brie increases by 4
        app.updateItems()
        assertEquals(11, brie.quality)
    }

    @Test
    fun conjured_passes() {
        //Checks that passes increase at twice the usual rate (+2)
        val item = Item("Conjured Backstage passes", 11, 5)
        val app = single_item_setup(item)
        app.updateItems()
        assertEquals(7, item.quality)
        //Checks that the Conjured, passes with 10 or fewer days to go increase by 4 (+4)
        app.updateItems()
        assertEquals(11, item.quality)
    }

    @Test
    fun conjured_sulfuras_doesnt_change_in_quality() {
        val item = update_item_once(Item("Conjured Sulfuras", 11, 5))
        assertEquals(5, item.quality)
    }
    @Test
    fun case_independance(){
        val app=GildedRose(arrayOf(
            Item("cOnjuRed bRie 7",6,10),
            Item("bAckstage PaSses to TROMBONE-101",3,7),
            Item("Our lord SulFuras's toeNails",10,3)
        ))
        app.updateItems()
        assertEquals(8,app.items[0].quality)
        assertEquals(10,app.items[1].quality)
        assertEquals(10,app.items[2].sellIn)
        assertEquals(3,app.items[2].quality)
    }
}


