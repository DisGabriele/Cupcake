package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Flavor(var name: String, listColor: List<Int>) {

    private val _quantity = MutableLiveData(0)
    var quantity: LiveData<Int> = _quantity

    private val colorMap: MutableMap<String,Int> = mutableMapOf(
        Pair("main_color",0),
        Pair("shade_color",0),
        Pair("light_color1",0),
        Pair("light_color2",0)
    )

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
    }

    fun getColorMap(): MutableMap<String,Int>{
        return colorMap
    }
    init{
        colorMap["main_color"] = listColor[0]
        colorMap["shade_color"] = listColor[1]
        colorMap["light_color1"] = listColor[2]
        colorMap["light_color2"] = listColor[3]
    }
}