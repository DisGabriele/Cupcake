package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.cupcake.Data.Datasource
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val PRICE_PER_CUPCAKE = 2.00

private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _remainingQuantity = MutableLiveData<Int>()
    val remainingQuantity: LiveData<Int> = _remainingQuantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor


    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

   // private val _dataset = MutableLiveData<MutableList<Flavor>>()
    // val dataset: MutableLiveData<MutableList<Flavor>> = _dataset

    val dataset: List<Flavor> = Datasource.flavors

    val dateOptions = getPickupOptions()

    init {
        resetOrder()
    }

    fun getFlavorQuantityList(): List<Flavor> {
        return dataset
    }


    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        // If the user selected the first option (today) for pickup, add the surcharge
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        _remainingQuantity.value = numberCupcakes
        updatePrice()
    }

    fun setRemainingQuantity(numberCupcakes: Int) {
        _remainingQuantity.value = numberCupcakes
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }
    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }


    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // Create a list of dates starting with the current date and the following 3 dates
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _price.value = 0.0
    }

}