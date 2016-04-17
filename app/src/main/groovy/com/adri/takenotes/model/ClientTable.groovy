package com.adri.takenotes.model

import android.os.Parcel
import android.os.Parcelable

class ClientTable implements Serializable{

    int numberTable
    String name
    LinkedList<Dish> dishes

    public ClientTable(int numberTable, String name){
        this.numberTable = numberTable
        this.name = name
        dishes = new LinkedList<>();
        dishes.add(new Dish(name: "Lentejas", description: "Plato de casa de la abuela", allergens: [AllergensEnums.ALTRAMUCES,AllergensEnums.CRUSTACEOS], price: 2f, quantity: 1))
        dishes.add(new Dish(name: "Sopa", description: "Plato que le encanta a la Patri", allergens: [], price: 1f, quantity: 5))
    }

    @Override
    String toString() {
        return numberTable + " - " + name.toString()
    }



}