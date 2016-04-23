package com.adri.takenotes.model

import groovy.transform.CompileStatic

enum AllergensEnums {
    GLUTEN,
    CRUSTACEOS,
    HUEVOS,
    PESCADO,
    CACAHUETES,
    SOJA,
    LACTEOS,
    FRUTOS_DE_CASCARA,
    APIO,
    MOSTAZA,
    GRANOS_DE_SESAMO,
    SULFITOS,
    MOLUSCOS,
    ALTRAMUCES

}

class Dish implements Serializable, Cloneable {
    int id
    String urlImage
    String name
    String description
    Float price
    int quantity
    AllergensEnums[] allergens
    String additions


    void addAllergens(LinkedList<String> allergens) {
        LinkedList<AllergensEnums> allergensEnumses = new LinkedList<>()
        for (String s : allergens) {
            AllergensEnums enumAllergen = AllergensEnums.valueOf(s)
            allergensEnumses.add(enumAllergen)
        }
        this.allergens = allergensEnumses.toArray()
    }

    Dish(int id, String name, String description, Float price, int quantity, LinkedList<AllergensEnums> enumses,
         String additions, String urlImage) {
        this.id = id
        this.name = name
        this.description = description
        this.price = price
        this.quantity = quantity
        this.allergens = enumses.toArray()
        this.additions = additions
        this.urlImage = urlImage
    }

    Dish getClone() {
        LinkedList<AllergensEnums> list = this.allergens.toList()
        return new Dish(this.id, this.name, this.description, this.price, this.quantity, list,
                 this.additions, this.urlImage)
    }


}