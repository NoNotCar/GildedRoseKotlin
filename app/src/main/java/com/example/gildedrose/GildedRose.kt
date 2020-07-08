package com.example.gildedrose

class GildedRose(var items: Array<Item>) {
    fun age(i:Item){
        if (!i.name.contains("Sulfuras")){
            i.sellIn--
        }
    }
    fun getQualityDecay(i:Item):Int{
        if (i.name.contains("Aged Brie")){
            return 1
        }else if (i.name.contains("Backstage passes")){
            return when{
                (i.sellIn<=0)-> -i.quality //expired ticket, quality>=0 so doubling won't affect this
                (i.sellIn<5)-> 3
                (i.sellIn<10)-> 2
                else-> 1
            }
        } else {
            return -1
        }
    }
    fun updateQuality(i:Item){
        if (i.name.contains("Sulfuras")){
            return
        }
        var qDecay=getQualityDecay(i)
        if (i.sellIn<=0){
            qDecay*=2
        }
        if (i.name.contains("Conjured")){
            qDecay*=2
        }
        i.quality+=qDecay
        //clamp quality
        i.quality=when{(i.quality<0)->0;(i.quality>50)->50;else->i.quality}
    }
    fun updateItems() {
        items.forEach{
            age(it)
            updateQuality(it)
            //bop(it)
        }
    }

}

