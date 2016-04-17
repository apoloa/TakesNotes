package com.adri.takenotes.model

import android.media.Image
import android.os.Parcel
import android.os.Parcelable;

enum AllergensEnums{
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

class Dish implements Serializable{

    String name
    String description
    Float price
    int quantity
    AllergensEnums[] allergens

}