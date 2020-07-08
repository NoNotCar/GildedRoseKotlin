package com.example.gildedrose

import org.junit.Assert.*
import org.junit.Test

class GildedRoseTest {
    fun single_item_setup(i:Item):GildedRose{
        return GildedRose(arrayOf(i))
    }
    fun update_item_once(i:Item):Item{
        val app=single_item_setup(i)
        app.updateItems()
        return app.items[0]
    }
    @Test fun basic_items_quality_falls_once_per_day() {
        val item = update_item_once(Item("spam", 10, 5))
        assertEquals("spam", item.name)
        assertEquals(4,item.quality)
    }

    @Test fun basic_items_sellin_falls_once_per_day() {
        val item = update_item_once(Item("spam", 10, 5))
        assertEquals("spam", item.name)
        assertEquals(9, item.sellIn)

    }
    @Test fun expired_items_degrade_twice_as_fast(){
        val item = update_item_once(Item("dodgy milk", 0, 5))
        assertEquals(3,item.quality)
    }
    @Test fun quality_doesnt_exceed_50(){
        val app2=single_item_setup(Item("Aged Brie",10,50))
        app2.updateItems()
        assertEquals(50,app2.items[0].quality)
    }

    @Test fun quality_is_never_negative(){
        val item = update_item_once(Item("pile of rust", 0, 0))
        assertEquals(0,item.quality)

        //Check this is still true for a "worst case" conjured, expired item
        val item2 = update_item_once(Item("Conjured something", 0, 1))
        assertEquals(0,item2.quality)
    }

    @Test fun brie_quality_increases_with_time(){
        val item = update_item_once(Item("Aged Brie",10,30))
        assertEquals(31,item.quality)
    }

    @Test fun Sulfuras_is_legendary_and_so_does_not_degrade(){
        val item = update_item_once(Item("Sulfuras, Hand of Ragnaros",10,30))
        assertEquals(30,item.quality)
        assertEquals(10,item.sellIn)

    }
    @Test fun Backstage_Passes_up_by_1_with_time(){
        val item = update_item_once(Item("Backstage passes to a concert",11,30))
        assertEquals(31,item.quality)
    }



    @Test fun Backstage_Passes_up_by_2_with_time_between_10_and_6_days(){
        val item = update_item_once(Item("Backstage passes to a concert",10,30))
        assertEquals(32,item.quality)
        val item2 = update_item_once(Item("Backstage passes to a concert",6,30))
        assertEquals(32,item2.quality)

    }

    @Test fun Backstage_Passes_up_by_3_with_time_between_5_and_1_days(){
        val item = update_item_once(Item("Backstage passes to a concert",5,30))
        assertEquals(33,item.quality)
        val item2 = update_item_once(Item("Backstage passes to a concert",2,30))
        assertEquals(33,item.quality)
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
        val item = update_item_once(Item("Conjured spam", 1, 7))
        assertEquals(3, item.quality)


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
        val item = update_item_once(Item("Conjured Sulfuras", 11, 5))
        assertEquals(5,item.quality)
    }
}


