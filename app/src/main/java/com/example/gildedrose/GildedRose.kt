package com.example.gildedrose

class GildedRose(var items: Array<Item>) {
    fun age(i:Item){
        if (!i.name.contains("Sulfuras")){
            i.sellIn--
        }
    }
    fun getDelta(i:Item):Int{
        if (i.name.contains("Aged Brie")){
            return 1
        }else if (i.name.contains("Backstage passes")){
            if (i.sellIn<=0){
                i.quality=0 //expired ticket, set quality to 0 without delta as don't want doubling
                return 0
            }
            else if (i.sellIn<5){
                return 3
            }else if (i.sellIn<10){
                return 2
            }else{
                return 1
            }
        } else {
            return -1
        }
    }
    fun decay(i:Item){
        if (i.name.contains("Sulfuras")){
            return
        }
        var delta=getDelta(i)
        if (i.sellIn<=0){
            delta*=2
        }
        if (i.name.contains("Conjured")){
            delta*=2
        }
        i.quality+=delta
        //clamp quality
        if (i.quality<0){
            i.quality=0
        }else if (i.quality>50){
            i.quality=50
        }
    }
    fun updateQuality() {
        items.forEach{
            age(it)
            decay(it)
            //bop(it)
        }
    }

}

