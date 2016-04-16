package com.adri.takenotes.model;

class Restaurant {
    LinkedList<ClientTable> tables

    public Restaurant(){
        tables = new LinkedList<>();
        tables.add(new ClientTable(numberTable: tables.size()+1, name: "Hola Mundio"))

    }

    public void addNewTable(String name){
        tables.add(new ClientTable(numberTable: tables.size()+1, name:name))
    }

    public int numberTables(){
        return tables.size() + 1
    }
}