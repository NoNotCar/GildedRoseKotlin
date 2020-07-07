package com.example.gildedrose

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        for (i in items) {
            if (i.name=="Sulfuras"){
                continue //sulfuras doesn't age or decay
            }else{
                i.sellIn--
            }
            var delta=0 //amount quality changes by
            if (i.name == "Aged Brie"){
                delta=1
            }else if (i.name.contains("Backstage passes")){
                if (i.sellIn<10){
                    delta=2
                }else if (i.sellIn<5){
                    delta=3
                }else if (i.sellIn<=0){
                    i.quality=0 //expired ticket, set quality to 0 without delta as don't want doubling
                }else{
                    delta=1
                }
            } else {
                delta=-1
            }
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
    }

}

