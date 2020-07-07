package com.example.gildedrose

import org.junit.Assert.*
import org.junit.Test

class GildedRoseTest {
    fun single_item_setup(i:Item):GildedRose{
        return GildedRose(arrayOf(i))
    }
    @Test fun basic_item() {
        val app = single_item_setup(Item("spam", 10, 5))
        app.updateQuality()
        assertEquals("spam", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(4,app.items[0].quality)

    }
    @Test fun expired_items_degrade_twice_as_fast(){
        //checks that expired items degrade twice as fast
        val app = single_item_setup(Item("dodgy milk", 0, 5))
        app.updateQuality()
        assertEquals(3,app.items[0].quality)
    }
    @Test fun quality_limits(){
        //checks that quality cannot exceed 50, and cannot go below 0
        val app = single_item_setup(Item("pile of rust", 0, 0))
        app.updateQuality()
        assertEquals(0,app.items[0].quality)
        val app2=single_item_setup(Item("Aged Brie",10,50))
        app2.updateQuality()
        assertEquals(50,app2.items[0].quality)
    }
    @Test fun brie_increases(){
        //checks that the value of brie increases with time
        val app = single_item_setup(Item("Aged Brie",10,30))
        app.updateQuality()
        assertEquals(31,app.items[0].quality)
    }

    @Test fun Sulfuras_is_legendary(){
        //checks that Sellin (the days to the sell-by-date) do not decrease for "Sulfuras, Hand of Ragnaros"
        //also checks that the value of the item does not reduce.
        val app = single_item_setup(Item("Sulfuras, Hand of Ragnaros",10,30))
        app.updateQuality()
        assertEquals(30,app.items[0].quality)
        assertEquals(10,app.items[0].sellIn)

    }
    @Test fun Backstage_Passes(){
        val cases= arrayOf(Pair(11,31), Pair(10,32),Pair(6,32),Pair(5,33),Pair(0,0))
        val app=GildedRose(cases.map { Item("Backstage passes to a TAFKAL80ETC concert",it.first,30) }.toTypedArray())
        app.updateQuality()
        cases.forEachIndexed{i,p-> assertEquals(p.second,app.items[i].quality)}
    }

    @Test fun Conjured(){
        //Checks that ordinary "Conjured" items degrade 2 per day
        val app = single_item_setup(Item("Conjured spam", 10, 3))
        app.updateQuality()
        assertEquals("Conjured spam", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(1,app.items[0].quality)
        //Checks that the quality can't go negative
        app.updateQuality()
        assertEquals(0,app.items[0].quality)
    }

    @Test fun Conjured_brie(){
        //Checks that Conjured brie increases by 2
        val app = single_item_setup(Item("Conjured Aged Brie", 2, 5))
        app.updateQuality()
        assertEquals(7,app.items[0].quality)
        //Checks that the Conjured, expired brie increases by 4
        app.updateQuality()
        assertEquals(11,app.items[0].quality)
    }

    @Test fun Conjured_passes(){
        //Checks that passes increase at twice the usual rate (+2)
        val app = single_item_setup(Item("Conjured Backstage passes", 11, 5))
        app.updateQuality()
        assertEquals(7,app.items[0].quality)
        //Checks that the Conjured, passes with 10 or fewer days to go increase by 4 (+4)
        app.updateQuality()
        assertEquals(11,app.items[0].quality)
    }

    @Test fun Conjured_Sulfuras(){
        //Checks that conjured sulfuras doesn't change in quality
        val app = single_item_setup(Item("Conjured Sulfuras", 11, 5))
        app.updateQuality()
        assertEquals(5,app.items[0].quality)
    }

}


