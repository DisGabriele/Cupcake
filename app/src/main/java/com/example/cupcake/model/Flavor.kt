package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Flavor(var name: String) {

    private val _quantity = MutableLiveData(0)
    var quantity: LiveData<Int> = _quantity

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
    }
}