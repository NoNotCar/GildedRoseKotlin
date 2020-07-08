package com.example.gildedrose

import org.junit.Assert.*
import org.junit.Test

class GildedRoseTest {
    fun single_item_setup(i:Item):GildedRose{
        return GildedRose(arrayOf(i))
    }
    @Test fun basic_items_quality_falls_once_per_day() {
        val app = single_item_setup(Item("spam", 10, 5))
        app.updateItems()
        assertEquals("spam", app.items[0].name)
        assertEquals(4,app.items[0].quality)

    }

    @Test fun basic_items_sellin_falls_once_per_day() {
        val app = single_item_setup(Item("spam", 10, 5))
        app.updateItems()
        assertEquals("spam", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)

    }
    @Test fun expired_items_degrade_twice_as_fast(){
        val app = single_item_setup(Item("dodgy milk", 0, 5))
        app.updateItems()
        assertEquals(3,app.items[0].quality)
    }
    @Test fun quality_doesnt_exceed_50(){
        val app2=single_item_setup(Item("Aged Brie",10,50))
        app2.updateItems()
        assertEquals(50,app2.items[0].quality)
    }

    @Test fun quality_is_never_negative(){
        val app = single_item_setup(Item("pile of rust", 0, 0))
        app.updateItems()
        assertEquals(0,app.items[0].quality)

        //Check this is still true for a "worst case" conjured, expired item
        val app1 = single_item_setup(Item("Conjured something", 0, 1))
        app1.updateItems()
        assertEquals(0,app1.items[0].quality)
    }

    @Test fun brie_quality_increases_with_time(){
        val app = single_item_setup(Item("Aged Brie",10,30))
        app.updateItems()
        assertEquals(31,app.items[0].quality)
    }

    @Test fun Sulfuras_is_legendary_and_so_does_not_degrade(){
        val app = single_item_setup(Item("Sulfuras, Hand of Ragnaros",10,30))
        app.updateItems()
        assertEquals(30,app.items[0].quality)
        assertEquals(10,app.items[0].sellIn)

    }
    @Test fun Backstage_Passes_up_by_1_with_time(){
        val app = single_item_setup(Item("Backstage passes to a concert",11,30))
        app.updateItems()
        assertEquals(31,app.items[0].quality)
    }



    @Test fun Backstage_Passes_up_by_2_with_time_between_10_and_6_days(){
        val app = single_item_setup(Item("Backstage passes to a concert",10,30))
        app.updateItems()
        assertEquals(32,app.items[0].quality)
        val app1 = single_item_setup(Item("Backstage passes to a concert",6,30))
        app1.updateItems()
        assertEquals(32,app1.items[0].quality)

    }

    @Test fun Backstage_Passes_up_by_3_with_time_between_5_and_1_days(){
        val app = single_item_setup(Item("Backstage passes to a concert",5,30))
        app.updateItems()
        assertEquals(33,app.items[0].quality)
        val app1 = single_item_setup(Item("Backstage passes to a concert",2,30))
        app1.updateItems()
        assertEquals(33,app1.items[0].quality)
    }

    @Test fun Backstage_Passes_are_worthless_if_expired(){
        val app = single_item_setup(Item("Backstage passes to a concert",1,30))
        app.updateItems()
        assertEquals(0,app.items[0].quality)
        app.updateItems()
        assertEquals(0,app.items[0].quality)
    }





    @Test fun Conjured_items_degrade_faster(){
        //Checks that ordinary "Conjured" items degrade 2 per day
        val app = single_item_setup(Item("Conjured spam", 10, 3))
        app.updateItems()
        assertEquals("Conjured spam", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(1,app.items[0].quality)
        //Checks that the quality can't go negative
        app.updateItems()
        assertEquals(0,app.items[0].quality)
    }

    @Test fun conjured_expired_degrade_at_4_per_day(){
        val app = single_item_setup(Item("Conjured spam", 1, 7))
        app.updateItems()
        assertEquals(3, app.items[0].quality)


    }

    @Test fun Conjured_brie(){
        //Checks that Conjured brie increases by 2
        val app = single_item_setup(Item("Conjured Aged Brie", 2, 5))
        app.updateItems()
        assertEquals(7,app.items[0].quality)
        //Checks that the Conjured, expired brie increases by 4
        app.updateItems()
        assertEquals(11,app.items[0].quality)
    }

    @Test fun Conjured_passes(){
        //Checks that passes increase at twice the usual rate (+2)
        val app = single_item_setup(Item("Conjured Backstage passes", 11, 5))
        app.updateItems()
        assertEquals(7,app.items[0].quality)
        //Checks that the Conjured, passes with 10 or fewer days to go increase by 4 (+4)
        app.updateItems()
        assertEquals(11,app.items[0].quality)
    }

    @Test fun Conjured_Sulfuras_doesnt_change_in_quality(){
        //Checks that conjured sulfuras doesn't change in quality
        val app = single_item_setup(Item("Conjured Sulfuras", 11, 5))
        app.updateItems()
        assertEquals(5,app.items[0].quality)
    }
}


