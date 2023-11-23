package com.example.cupcake.Data

import com.example.cupcake.model.Flavor

object Datasource {

    val flavors: List<Flavor> = listOf(
        Flavor("Vanilla", listOf(-8014, -13948, -5183, -5183)),
        Flavor("Chocolate", listOf(-10865366, -12704743, -8431806, -8431806)),
        Flavor("Red Velvet", listOf(-7667712, -8388608, -5103070, -5103070)),
        Flavor("Salted Caramel", listOf(-5093106, -7198464, -2987746, -2987746)),
        Flavor("Coffee", listOf(-9482697,-11060187, -7574445, -7574445)),
        Flavor("Strawberry", listOf(-1023342,-1294214, -749647, -749647))
    )
}