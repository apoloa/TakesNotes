package com.adri.takenotes.model;

class ClientTable {
    int numberTable
    String name

    @Override
    String toString() {
        return numberTable + " - " + name.toString()
    }
}